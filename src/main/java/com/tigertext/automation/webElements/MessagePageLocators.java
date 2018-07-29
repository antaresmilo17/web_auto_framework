package com.tigertext.automation.webElements;

import org.openqa.selenium.By;
import org.springframework.stereotype.Component;

@Component
public class MessagePageLocators {
    public MessagePageLocators(){
    }

    public final String newMessageButtonPath = "[data-class='home.conv.single.newConvBtn']";
    public final String searchInputFieldPath =  "select2-input";
    public final String userDropDownValuePath = "[data-class='home.name boldtokens']";
    public final String textInputFieldPath = "[data-class=\"home.conv.outgoing_message\"]";
    public final String sendMessageButtonPath = "[data-class='home.conv.send'][value='Send']";
    public final String messageSentStatusPath = "(//*[@data-class='home.conv.message.cntr'])[last()] //div[@class='messageSent']";
    public final String messageDeliveredStatusPath = "(//*[@data-class='home.conv.message.cntr'])[last()] //div[@class='messageDelivered']";
    public final String messageContentPath = "(//*[@data-class='home.conv.message.body'])[last()]";
    public final String messageContentEveryonePath = "(//*[@data-class='home.conv.message.body']/a)[last()]";
    public final String conversationControllerPath = "conversationCntr";
    public final String cancelMessageButtonPath = "//*[contains(text(), 'Cancel')][@data-class='home.conv.single.newConvBtn']";
    public final String messageRecallButtonPath = "(//*[@data-class='home.conv.message.recall'])[last()]";
    public final String messageMoreOptionsPath = "(//*[@data-class='home.conv.message.more'])[last()]";
    public final String moreClassPath = "(//*[@data-class='home.conv.message.cntr'])[last()]";
    public final String messageInfoPath = "(//*[@data-class='home.conv.message.message_info'])[last()]";
    public final String printPreviewButtonPath = "a[class='printPreview']";
    public final String muteButtonPath = ".muteToggle";
    public final String alreadyMutedButtonPath =  "//div[@class='icons']/descendant::*[@class='muteToggle muted']";
    public final String priorityMessageIntroPath = "[data-class='home.conv.single.pmOverlayClose']";
    public final String priorityMessagePath = ".pm>a";
    public final String priorityMessageHeaderPath = "(//*[@class='priorityCntr'])[last()]";
    public final String priorityMessageConfirmPath = ".confirm";
    public final String emojiButtonPath = ".emojiInsert";
    public final String emotionPointUpPath = "//*[@data-shortcode='point_up']";
    public final String emojiMessagePath = "(//*[@data-class='home.conv.message.body'])[last()]//span[@class='em emj49']";
    public final String clearConversationButtonPath = ".trash";
    public final String confirmClearConversationButtonPath = "[data-class='home.conv.clear_conversation']";
    public final String imageMessagePath = ".inlineImg";
    public final String groupSettingsPath = "[data-class='home.group.edit_group_btn']";
    public final String groupAvatarPath = "[data-class='home.avatar home.present']";
    public final String searchMessageListPath = "//*[@class='select2-choices']";
    public final String leaveForumButtonPath = "[data-class='home.conv.confirm_leave']";
    public final String cancelLeaveForumButtonPath = "#confirmLeaveRoom > .modal-footer > .btn.btn-success.btn-clear-v4";
    public final String confirmLeaveForumButtonPath = "[data-class='home.conv.leave_room']";
    public final String hideForumBannerPath = "[data-class='home.conv.rooms.hideRoomBanner']";
    public final String emojiPickerPath = "//*[@class='emojiPicker']";

    private final By newMessageButton = By.cssSelector(newMessageButtonPath);
    private final By searchInputField =  By.className(searchInputFieldPath);
    private final By userDropDownValue = By.cssSelector(userDropDownValuePath);
    private final By textInputField = By.cssSelector(textInputFieldPath);
    private final By sendMessageButton = By.cssSelector(sendMessageButtonPath);
    private final By messageSentStatus = By.xpath(messageSentStatusPath);
    private final By emojiPicker = By.xpath(emojiPickerPath);
    private final By messageDeliveredStatus = By.xpath(messageDeliveredStatusPath);
    private final By messageContent = By.xpath(messageContentPath);
    private final By messageContentEveryone = By.xpath(messageContentEveryonePath);
    private final By conversationController = By.className(conversationControllerPath);
    private final By cancelMessageButton = By.xpath(cancelMessageButtonPath);
    private final By messageRecallButton = By.xpath(messageRecallButtonPath);
    private final By messageMoreOptions = By.xpath(messageMoreOptionsPath);
    private final By moreClass = By.xpath(moreClassPath);
    private final By messageInfo = By.xpath(messageInfoPath);
    private final By printPreviewButton = By.cssSelector(printPreviewButtonPath);
    private final By muteButton = By.cssSelector(muteButtonPath);
    private final By alreadyMutedButton =  By.xpath(alreadyMutedButtonPath);
    private final By priorityMessageIntro = By.cssSelector(priorityMessageIntroPath);
    private final By priorityMessage = By.cssSelector(priorityMessagePath);
    private final By priorityMessageHeader = By.xpath(priorityMessageHeaderPath);
    private final By priorityMessageConfirm = By.cssSelector(priorityMessageConfirmPath);
    private final By emojiButton = By.cssSelector(emojiButtonPath);
    private final By emotionPointUp = By.xpath(emotionPointUpPath);
    private final By emojiMessage = By.xpath(emojiMessagePath);
    private final By clearConversationButton = By.cssSelector(clearConversationButtonPath);
    private final By confirmClearConversationButton = By.cssSelector(confirmClearConversationButtonPath);
    private final By imageMessage = By.cssSelector(imageMessagePath);
    private final By groupSettings = By.cssSelector(groupSettingsPath);
    private final By groupAvatar = By.cssSelector(groupAvatarPath);
    private final By searchMessageList = By.xpath(searchMessageListPath);
    private final By leaveForumButton = By.cssSelector(leaveForumButtonPath);
    private final By cancelLeaveForumButton = By.cssSelector(cancelLeaveForumButtonPath);
    private final By confirmLeaveForumButton = By.cssSelector(confirmLeaveForumButtonPath);
    private final By hideForumBanner = By.cssSelector(hideForumBannerPath);

    public By getEmojiPicker() {
        return emojiPicker;
    }
    public By getNewMessageButton() { return newMessageButton;  }
    public By getSearchInputField() { return searchInputField;  }
    public By getUserDropDownValue() {return userDropDownValue; }
    public By getTextInputField() {return textInputField;}
    public By getSendMessageButton() {return sendMessageButton;}
    public By getMessageSentStatus() { return messageSentStatus; }
    public By getMessageContent() { return messageContent; }
    public By getConversationController() {return conversationController;}
    public By getMessageDeliveredStatus() { return messageDeliveredStatus;}
    public By getCancelMessageButton() {return cancelMessageButton; }
    public By getMessageRecallButton() { return messageRecallButton; }
    public By getMessageMoreOptions() { return messageMoreOptions;  }
    public By getMoreClass(){ return moreClass;}
    public By getMessageInfo() { return messageInfo; }
    public By getPrintPreviewButton() {return printPreviewButton;}
    public By getMuteButton() { return muteButton;}
    public By getAlreadyMutedButton() {return alreadyMutedButton;}
    public By getPriorityMessage() {return priorityMessage;}
    public By getPriorityMessageHeader() {return priorityMessageHeader;}
    public By getPriorityMessageConfirm() {return priorityMessageConfirm;}
    public By getPriorityMessageIntro() {return priorityMessageIntro;}
    public By getEmojiButton() { return emojiButton;}
    public By getEmotionPointUp() {return emotionPointUp;   }
    public By getEmojiMessage() {return emojiMessage;}
    public By getClearConversationButton() { return clearConversationButton;}
    public By getConfirmClearConversationButton() {return confirmClearConversationButton;}
    public By getImageMessage() { return imageMessage;}
    public By getGroupSettings() {return groupSettings;}
    public By getGroupAvatar() {return groupAvatar;}
    public By getSearchMessageList() {return searchMessageList;}
    public By getLeaveForumButton() {return leaveForumButton;}
    public By getCancelLeaveForumButton() {return cancelLeaveForumButton;}
    public By getConfirmLeaveForumButton() {return confirmLeaveForumButton;}
    public By getHideForumBanner() {return hideForumBanner;}
    public By getMessageContentEveryone() { return messageContentEveryone; }
}
