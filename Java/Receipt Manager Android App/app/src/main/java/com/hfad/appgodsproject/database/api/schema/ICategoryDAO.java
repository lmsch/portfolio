package com.hfad.appgodsproject.database.api.schema;

import com.hfad.appgodsproject.pojos.Category;

import java.util.List;

public interface ICategoryDAO {
    public Category fetchCategoryById(long categoryId);
    public long addCategory(Category category);
    public List<Long> addCategories(List<Category> categoryList);
    public List<Category> fetchCategoriesByItemId(long itemId);
    public List<Category> fetchAllCategories();
}
