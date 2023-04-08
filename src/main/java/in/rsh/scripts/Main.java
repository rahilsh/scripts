package in.rsh.scripts;

import static com.google.inject.Guice.createInjector;

import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.google.inject.Provides;
import com.google.inject.name.Named;
import com.google.inject.name.Names;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import org.apache.commons.configuration.ConfigurationConverter;
import org.apache.commons.configuration.PropertiesConfiguration;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Main {

  private static Injector injector;

  private Main() {}

  public static Injector getInjector() {
    if (injector == null) {
      injector =
          createInjector(
              new AbstractModule() {
                @Override
                protected void configure() {
                  try {
                    Properties props =
                        ConfigurationConverter.getProperties(
                            new PropertiesConfiguration("config/local.properties"));
                    Names.bindProperties(binder(), props);
                    System.out.println("Properties binding done");
                  } catch (Exception e) {
                    e.printStackTrace();
                  }
                }

                @Provides
                public Retrofit getRetrofit(@Named("endpoint") String endpoint) {

                  OkHttpClient client =
                      new OkHttpClient.Builder()
                          .readTimeout(60, TimeUnit.SECONDS)
                          .connectTimeout(60, TimeUnit.SECONDS)
                          .build();

                  return new Retrofit.Builder()
                      .client(client)
                      .baseUrl(endpoint)
                      .addConverterFactory(GsonConverterFactory.create())
                      .build();
                }
              });
    }
    return injector;
  }
}
