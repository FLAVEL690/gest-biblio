package com.king.Bibliotheque.Services;

import com.king.Bibliotheque.Exceptions.RoleNotFoundException;
import com.king.Bibliotheque.Models.Books;
import com.king.Bibliotheque.Models.Category;
import com.king.Bibliotheque.Repositories.BooksRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class BooksServiceImpl implements BooksService{
    private BooksRepository booksRepository;

    public List<Books> search(){
        return this.booksRepository.findAll();
    }

    public Books getBookById(int id) {
        Optional<Books> optionalBook = this.booksRepository.findById(id);
        return optionalBook.orElse(null);
    }

    public void deleteBook(int id) {
        Optional<Books> category = booksRepository.findById(id);
        if (category.isPresent()) {
            booksRepository.deleteById(id);
        } else {
            throw new RoleNotFoundException("Book not found");
        }
    }

    @Override
    public void saveBook(Books book) {
        booksRepository.save(book);
    }
}
