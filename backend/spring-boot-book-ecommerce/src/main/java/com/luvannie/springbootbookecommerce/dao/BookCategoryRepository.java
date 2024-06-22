package com.luvannie.springbootbookecommerce.dao;

import com.luvannie.springbootbookecommerce.entity.BookCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@RepositoryRestResource(collectionResourceRel = "bookCategory", path = "book-category")
/*
* Annotation `@RepositoryRestResource` trong Spring Data REST được sử dụng để tùy chỉnh xuất ra của một repository REST.
* Trong trường hợp của bạn, nó được sử dụng để tùy chỉnh cả đường dẫn và tên của tài nguyên trả về.

- `collectionResourceRel`: Đây là tên của tài nguyên mà bạn muốn xuất hiện trong JSON HAL (_Hypertext Application Language_)
trả về. Trong trường hợp này, tên của tài nguyên sẽ là `bookCategory`.

- `path`: Đây là đường dẫn của API mà bạn muốn tạo. Trong trường hợp này, API sẽ có đường dẫn là `/book-category`.

Vì vậy, khi bạn gọi API, bạn sẽ thấy một tài nguyên `bookCategory` tại đường dẫn `/book-category`.*/
@CrossOrigin("http://localhost:4200")
public interface BookCategoryRepository extends JpaRepository<BookCategory,Long> {
    Page<BookCategory> findAll( Pageable pageable);
}
