package com.example.processor;

import ink.on.central.bot.BotInstance;
import ink.on.central.bot.annotation.MiraBotProcessor;
import ink.on.central.bot.entity.event.message.GroupMessageEvent;
import ink.on.central.bot.entity.message.MessagePart;
import ink.on.central.bot.template.message.PTGroupMessage;
import ink.on.central.bot.utils.MessageBuilderUtil;
import ink.on.central.bot.utils.MessageCheckUtil;

import java.util.ArrayList;
import java.util.List;

// 这个注解是必要的用于表示这是一个事件处理器，有这个注解才会被扫描和注册
@MiraBotProcessor
@SuppressWarnings("unused")
// 继承PTGroupMessage说明这个处理器用于处理群消息
public class GroupMsgProcessor extends PTGroupMessage {

  // 构造器 一般不用动
  public GroupMsgProcessor(BotInstance instance) {
    super(instance);
  }

  @Override
  public void process(GroupMessageEvent data, Long receivedTime) throws Exception {
    // 通过信息检查工具类检查接收到的信息为单一文本信息ping!
    if (MessageCheckUtil.isPureTextMessageEquals(data.getMessage(), "ping!")) {
      // 创建一个信息片段
      List<MessagePart<?>> messagePartList = new ArrayList<>();
      // 添加一个文本片段
      messagePartList.add(
        MessageBuilderUtil.Part.text(
          "pong! %sms".formatted(System.currentTimeMillis() - receivedTime)
        )
      );
      // 发送群消息
      sender.sendGroupMessage(data.getGroupId(), messagePartList);
    }
  }

  @Override
  public void errorHandler(Exception ex) {
    // 这里可以做错误处理, process 方法抛出的错误会被这里接收到
    super.errorHandler(ex);
  }

}
