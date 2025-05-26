package com.example.mcnews.ui.edit;

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
public final class EditArticleViewModel_Factory implements Factory<EditArticleViewModel> {
  private final Provider<ArticleRepository> repoProvider;

  public EditArticleViewModel_Factory(Provider<ArticleRepository> repoProvider) {
    this.repoProvider = repoProvider;
  }

  @Override
  public EditArticleViewModel get() {
    return newInstance(repoProvider.get());
  }

  public static EditArticleViewModel_Factory create(Provider<ArticleRepository> repoProvider) {
    return new EditArticleViewModel_Factory(repoProvider);
  }

  public static EditArticleViewModel newInstance(ArticleRepository repo) {
    return new EditArticleViewModel(repo);
  }
}
