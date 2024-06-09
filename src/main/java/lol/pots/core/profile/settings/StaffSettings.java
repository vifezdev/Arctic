package lol.pots.core.profile.settings;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class StaffSettings {
    private boolean staffMode = false;
    private boolean vanished = false;
    private boolean staffChat = false;
}
