package com.example.mcnews.ui.articles;

import com.example.mcnews.domain.repo.ArticleRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class ArticlesViewModel_Factory implements Factory<ArticlesViewModel> {
  private final Provider<ArticleRepository> repoProvider;

  public ArticlesViewModel_Factory(Provider<ArticleRepository> repoProvider) {
    this.repoProvider = repoProvider;
  }

  @Override
  public ArticlesViewModel get() {
    return newInstance(repoProvider.get());
  }

  public static ArticlesViewModel_Factory create(Provider<ArticleRepository> repoProvider) {
    return new ArticlesViewModel_Factory(repoProvider);
  }

  public static ArticlesViewModel newInstance(ArticleRepository repo) {
    return new ArticlesViewModel(repo);
  }
}
