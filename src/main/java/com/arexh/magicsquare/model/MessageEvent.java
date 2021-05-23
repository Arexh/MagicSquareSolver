package com.arexh.magicsquare.model;

public class MessageEvent {

    public final MessageType messageType;
    public final Object messageContent;

    public MessageEvent(MessageType messageType, Object messageContent) {
        this.messageType = messageType;
        this.messageContent = messageContent;
    }

    public enum MessageType {
        UPDATE_SUDOKU_KEYBOARD,
    }
}
