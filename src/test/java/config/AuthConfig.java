package config;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "classpath:auth.properties",
        "system:properties"
})

public interface AuthConfig extends Config {
    @Config.Key("username.selenoid")
    String remote_username();

    @Config.Key("password.selenoid")
    String remote_password();
}