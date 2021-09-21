package io.patriot_framework.virtual_smart_home.house.device;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class FireplaceTest {

    Fireplace fireplace = new Fireplace("fireplace");

    @Test
    public void fireUp() {
        fireplace.setEnabled(true);
        assertThat(fireplace.isEnabled(), equalTo(true));
    }

    @Test
    public void extinguish() {
        fireplace.setEnabled(false);
        assertThat(fireplace.isEnabled(), equalTo(false));
    }
}
