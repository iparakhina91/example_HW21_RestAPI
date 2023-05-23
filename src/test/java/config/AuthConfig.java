package config;

import org.aeonbits.owner.Config;

//@Config.Sources({
       // "classpath:${env}.properties"
//})

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:properties",
        "classpath:auth.properties"
})

public interface AuthConfig extends Config {
   // @Config.Key("username.selenoid")
   // String remote_username();

   // @Config.Key("password.selenoid")
    //String remote_password();

    @Key("username.selenoid")
    String remote_username();

    @Key("password.selenoid")
    String remote_password();

    @Key("apiToken")
    String apiToken();

    @Key("usernameAllure")
    String usernameAllure();

    @Key("passwordAllure")
    String passwordAllure();
}