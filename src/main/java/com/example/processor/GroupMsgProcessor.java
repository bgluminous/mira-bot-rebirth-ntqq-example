package com.example.processor;

import ink.on.central.bot.BotInstance;
import ink.on.central.bot.annotation.MiraBotListener;
import ink.on.central.bot.entity.event.message.GroupMessageEvent;
import ink.on.central.bot.template.message.LTGroupMessage;
import ink.on.central.bot.utils.MessageBuilder;
import ink.on.central.bot.utils.MessageCheckUtil;

// 这个注解是必要的用于表示这是一个事件监听器，有这个注解才会被扫描和注册
@MiraBotListener
@SuppressWarnings("unused")
// 继承PTGroupMessage说明这个监听器用于处理群消息
public class GroupMsgProcessor extends LTGroupMessage {

  // 构造器 一般不用动
  public GroupMsgProcessor(BotInstance instance) {
    super(instance);
  }

  @Override
  public void process(GroupMessageEvent data, Long receivedTime) throws Exception {
    // 通过信息检查工具类检查接收到的信息为单一文本信息ping!
    if (MessageCheckUtil.isPureTextMessageEquals(data.getMessage(), "ping!")) {
      // 发送群消息
      sender.sendGroupMessage(
        data.getGroupId(),
        new MessageBuilder()
          .addText("pong! %sms".formatted(System.currentTimeMillis() - receivedTime))
      );
    }
  }

  @Override
  public void errorHandler(Exception ex) {
    // 这里可以做错误处理, process 方法抛出的错误会被这里接收到
    super.errorHandler(ex);
  }

}
