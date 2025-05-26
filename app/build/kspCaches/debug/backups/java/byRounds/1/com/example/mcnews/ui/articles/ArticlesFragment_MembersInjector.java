package com.example.mcnews.ui.articles;

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
public final class ArticlesFragment_MembersInjector implements MembersInjector<ArticlesFragment> {
  private final Provider<ApiService> apiProvider;

  public ArticlesFragment_MembersInjector(Provider<ApiService> apiProvider) {
    this.apiProvider = apiProvider;
  }

  public static MembersInjector<ArticlesFragment> create(Provider<ApiService> apiProvider) {
    return new ArticlesFragment_MembersInjector(apiProvider);
  }

  @Override
  public void injectMembers(ArticlesFragment instance) {
    injectApi(instance, apiProvider.get());
  }

  @InjectedFieldSignature("com.example.mcnews.ui.articles.ArticlesFragment.api")
  public static void injectApi(ArticlesFragment instance, ApiService api) {
    instance.api = api;
  }
}
