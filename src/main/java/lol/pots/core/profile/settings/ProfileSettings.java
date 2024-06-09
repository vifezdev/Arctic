package lol.pots.core.profile.settings;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ProfileSettings {
    private boolean globalChat, privateMessages, privateMessagingSounds;
}
