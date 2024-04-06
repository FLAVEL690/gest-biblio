package com.king.Bibliotheque.Controllers;

import com.king.Bibliotheque.Models.Books;
import com.king.Bibliotheque.Models.Category;
import com.king.Bibliotheque.Models.Localisation;
import com.king.Bibliotheque.Repositories.BooksRepository;
import com.king.Bibliotheque.Repositories.CategoryRepository;
import com.king.Bibliotheque.Repositories.LocalisationRepository;
import com.king.Bibliotheque.Services.BooksService;
import com.king.Bibliotheque.Services.CategoryService;
import com.king.Bibliotheque.Services.LocalisationService;
import com.king.Bibliotheque.dto.BookDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import com.king.Bibliotheque.ResponseData;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.springframework.ui.Model;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController
//@RequestMapping(path = "books")
public class BooksController {

    private CategoryRepository categoryService;

    private LocalisationRepository localisationService;
    @Value("${uploadDir}")
    private String uploadFolder;
    @Value("${uploadDir}")  // Spring résoudra le placeholder
    private String uploadDirectory;

    private BooksService booksService;

    public BooksController(BooksService booksService, CategoryRepository categoryService, LocalisationRepository localisationService) {
        this.booksService = booksService;
        this.categoryService = categoryService;
        this.localisationService = localisationService;
    }

//    private ImageGalleryService imageGalleryService;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @GetMapping(path = "book", produces = APPLICATION_JSON_VALUE)
    public List<Books> getBooks(){
        return this.booksService.search();
    }

    @GetMapping(path = "book/{id}", produces = APPLICATION_JSON_VALUE)
    public Books getBookById(@PathVariable int id){
        Books b;
        b = this.booksService.getBookById(id);
        return b;
    }

    @DeleteMapping(path = "book/{id}")
    public void deleteBook(@PathVariable int id) {
        booksService.deleteBook(id);
    }


    @PostMapping(path = "book", consumes = APPLICATION_JSON_VALUE)
    public void createBook(@RequestBody BookDto book){
        Optional<Category> optionalCategory = categoryService.findById(Integer.parseInt(book.getCategory()));
            Optional<Localisation> optionalPlace = localisationService.findById(Integer.parseInt(book.getPlace()));


            Books new_book = new Books();
            System.out.println("hey me voici, je suis un livre vide"+ new_book);
            new_book.setTitle(book.getTitle());
//            new_book.setImage(filePath);
            new_book.setAuthorName(book.getAuthorName());
            new_book.setEdition(book.getEdition());
            new_book.setDisponibility(true);
            new_book.setPublicationDate(LocalDate.parse(book.getPublicationDate()));
            System.out.println("hey me voici, je suis un livre presque plein"+ new_book);
            if (optionalCategory.isPresent() && optionalPlace.isPresent()) {
                Category categorys = optionalCategory.get();
                Localisation places = optionalPlace.get();

                // Enregistrement du livre avec les données converties
                new_book.setCategory(categorys);
                new_book.setPlace(places);
            }
//            book.setCategory(categorys);
//            book.setPlace(places);
            new_book.setNbExemplaire(Integer.parseInt(book.getNbExemplaire()));
            new_book.setISBN(book.getISBN());
            System.out.println("hey me voici, je suis un livre toto"+ new_book);

        this.booksService.saveBook(new_book);
    }

//    public @ResponseBody ResponseEntity<String> createBook(@RequestBody BookDto book,
//                                                           HttpServletRequest request) {
//        try {
//            System.out.println("yurtgrfd");
//            String uploadDirectory = request.getServletContext().getRealPath(uploadFolder);
//            String fileName = book.getImage().getOriginalFilename();
//            String filePath = Paths.get(uploadDirectory, fileName).toString();
//
//            if (fileName == null || fileName.contains("..")) {
//                return new ResponseEntity<>("Sorry! Filename contains invalid path sequence " + fileName, HttpStatus.BAD_REQUEST);
//            }
//
//            Optional<Category> optionalCategory = categoryService.findById(Integer.parseInt(book.getCategory()));
//            Optional<Localisation> optionalPlace = localisationService.findById(Integer.parseInt(book.getPlace()));
//
//
//            Books new_book = new Books();
//            System.out.println("hey me voici, je suis un livre vide"+ book);
//            new_book.setTitle(book.getTitle());
//            new_book.setImage(filePath);
//            new_book.setAuthorName(book.getAuthorName());
//            new_book.setEdition(book.getEdition());
//            new_book.setDisponibility(true);
//            new_book.setPublicationDate(LocalDate.parse(book.getPublicationDate()));
//            System.out.println("hey me voici, je suis un livre presque plein"+ new_book);
//            if (optionalCategory.isPresent() && optionalPlace.isPresent()) {
//                Category categorys = optionalCategory.get();
//                Localisation places = optionalPlace.get();
//
//                // Enregistrement du livre avec les données converties
//                new_book.setCategory(categorys);
//                new_book.setPlace(places);
//            }
////            book.setCategory(categorys);
////            book.setPlace(places);
//            new_book.setNbExemplaire(Integer.parseInt(book.getNbExemplaire()));
//            new_book.setISBN(book.getISBN());
//            System.out.println("hey me voici, je suis un livre toto"+ book);
//            booksService.saveBook(new_book);
//
//            return new ResponseEntity<>("Product Saved With File - " + fileName, HttpStatus.OK);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }


}
