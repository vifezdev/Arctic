package lol.pots.core.option;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author hieu
 * @date 09/10/2023
 */

@AllArgsConstructor
@Getter
@Setter
public class Option {

    private boolean globalChatEnabled, privateMessagesEnabled, soundsEnabled;

}
