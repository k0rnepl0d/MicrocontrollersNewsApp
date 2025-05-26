package com.example.mcnews.data.repo;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import okhttp3.OkHttpClient;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation"
})
public final class AuthRepositoryImpl_Factory implements Factory<AuthRepositoryImpl> {
  private final Provider<Context> contextProvider;

  private final Provider<OkHttpClient> clientProvider;

  public AuthRepositoryImpl_Factory(Provider<Context> contextProvider,
      Provider<OkHttpClient> clientProvider) {
    this.contextProvider = contextProvider;
    this.clientProvider = clientProvider;
  }

  @Override
  public AuthRepositoryImpl get() {
    return newInstance(contextProvider.get(), clientProvider.get());
  }

  public static AuthRepositoryImpl_Factory create(Provider<Context> contextProvider,
      Provider<OkHttpClient> clientProvider) {
    return new AuthRepositoryImpl_Factory(contextProvider, clientProvider);
  }

  public static AuthRepositoryImpl newInstance(Context context, OkHttpClient client) {
    return new AuthRepositoryImpl(context, client);
  }
}
