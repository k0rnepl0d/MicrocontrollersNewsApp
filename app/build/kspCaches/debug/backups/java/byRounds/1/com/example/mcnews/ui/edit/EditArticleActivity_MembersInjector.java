package com.example.mcnews.ui.edit;

import com.example.mcnews.data.remote.ApiService;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class EditArticleActivity_MembersInjector implements MembersInjector<EditArticleActivity> {
  private final Provider<ApiService> apiProvider;

  public EditArticleActivity_MembersInjector(Provider<ApiService> apiProvider) {
    this.apiProvider = apiProvider;
  }

  public static MembersInjector<EditArticleActivity> create(Provider<ApiService> apiProvider) {
    return new EditArticleActivity_MembersInjector(apiProvider);
  }

  @Override
  public void injectMembers(EditArticleActivity instance) {
    injectApi(instance, apiProvider.get());
  }

  @InjectedFieldSignature("com.example.mcnews.ui.edit.EditArticleActivity.api")
  public static void injectApi(EditArticleActivity instance, ApiService api) {
    instance.api = api;
  }
}
