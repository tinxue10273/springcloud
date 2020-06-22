package provider.kafka;

import provider.common.JsonTool;
import provider.convertor.DiscussPostConvertor;
import provider.domain.MessageDO;
import provider.dto.DiscussPostDTO;
import provider.dto.EventDTO;
import provider.service.DiscussPostService;
import provider.service.ElasticsearchService;
import provider.service.MessageService;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static provider.common.CycleConstant.SYSTEM_USER_ID;
import static provider.common.CycleConstant.TOPIC_COMMENT;
import static provider.common.CycleConstant.TOPIC_DELETE;
import static provider.common.CycleConstant.TOPIC_FOLLOW;
import static provider.common.CycleConstant.TOPIC_LIKE;
import static provider.common.CycleConstant.TOPIC_PUBLISH;

@Slf4j
@Component
public class EventConsumer {

    @Autowired
    private MessageService messageService;

    @Autowired
    private DiscussPostService discussPostService;

    @Autowired
    private ElasticsearchService elasticsearchService;

    @KafkaListener(topics = {TOPIC_COMMENT, TOPIC_LIKE, TOPIC_FOLLOW})
    public void handleCommentMessage(ConsumerRecord record) {
        if (isNull(record)) {
            return;
        }

        EventDTO event = JsonTool.fromJson(record.value().toString(), EventDTO.class);
        if (event == null) {
            log.error("消息格式错误!");
            return;
        }

        MessageDO message = MessageDO.builder()
                .fromId(SYSTEM_USER_ID)
                .toId(event.getEntityUserId())
                .conversationId(event.getTopic())
                .createTime(new Date())
                .build();

        Map<String, Object> content = new HashMap<>();
        content.put("userId", event.getUserId());
        content.put("entityType", event.getEntityType());
        content.put("entityId", event.getEntityId());

        if (!event.getData().isEmpty()) {
            for (Map.Entry<String, Object> entry : event.getData().entrySet()) {
                content.put(entry.getKey(), entry.getValue());
            }
        }

        message.setContent(JSONObject.toJSONString(content));
        messageService.add(message);
    }

    @KafkaListener(topics = {TOPIC_PUBLISH})
    public void handlePublishMessage(ConsumerRecord record) {
        if (isNull(record)) {
            return;
        }

        EventDTO event = JsonTool.fromJson(record.value().toString(), EventDTO.class);
        if (event == null) {
            return;
        }

        DiscussPostDTO post = DiscussPostConvertor.convertDTO(discussPostService.get(event.getEntityId()));
        elasticsearchService.saveDiscussPost(post);
    }

    @KafkaListener(topics = {TOPIC_DELETE})
    public void handleDeleteMessage(ConsumerRecord record) {
        if (isNull(record)) {
            return;
        }

        EventDTO event = JsonTool.fromJson(record.value().toString(), EventDTO.class);
        if (event == null) {
            log.error("消息格式错误!");
            return;
        }
        elasticsearchService.deleteDiscussPost(event.getEntityId());
    }

    private boolean isNull(ConsumerRecord record) {
        if (ObjectUtils.isEmpty(record) || ObjectUtils.isEmpty(record.value())) {
            log.error("消息的内容为空!");
            return true;
        }
        return false;
    }


}
