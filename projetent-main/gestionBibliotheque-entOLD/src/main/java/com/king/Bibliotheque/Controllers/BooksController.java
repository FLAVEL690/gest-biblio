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

    public BooksController(BooksService booksService) {
        this.booksService = booksService;
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


//    @PostMapping(path = "book")
//    public @ResponseBody
//    ResponseEntity<?> createBook(@RequestParam("title") String title,
//                                 @RequestParam("authorName") String authorName, @RequestParam("edition") String edition, @RequestParam("publicationDate") Date publicationDate, Model model, HttpServletRequest request
//             , @RequestParam("ISBN") String ISBN,@RequestParam("nbExemplaire") int nbExemplaire, @RequestParam("category") Category category, @RequestParam("place") Localisation place, final @RequestParam("image") MultipartFile file) {
//        try {
//            //String uploadDirectory = System.getProperty("user.dir") + uploadFolder;
//            String uploadDirectory = request.getServletContext().getRealPath(uploadFolder);
//            log.info("uploadDirectory:: " + uploadDirectory);
//            String fileName = file.getOriginalFilename();
//            String filePath = Paths.get(uploadDirectory, fileName).toString();
//            log.info("FileName: " + file.getOriginalFilename());
//            if (fileName == null || fileName.contains("..")) {
//                model.addAttribute("invalid", "Sorry! Filename contains invalid path sequence \" + fileName");
//                return new ResponseEntity<>("Sorry! Filename contains invalid path sequence " + fileName, HttpStatus.BAD_REQUEST);
//            }
//            String[] names = title.split(",");
//            String[] authorNames = authorName.split(",");
//            log.info("Name: " + names[0]+" "+filePath);
//            log.info("description: " + authorNames[0]);
//            try {
//                File dir = new File(uploadDirectory);
//                if (!dir.exists()) {
//                    log.info("Folder Created");
//                    dir.mkdirs();
//                }
//                // Save the file locally
//                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
//                stream.write(file.getBytes());
//                stream.close();
//            } catch (Exception e) {
//                log.info("in catch");
//                e.printStackTrace();
//            }
////            Category cat = new Category();
////            Localisation loc = new Localisation();
////            cat.setTitle(category);
////            loc.setColumnx(place);
////            loc.setLinex("");
//            byte[] imageData = file.getBytes();
//            Books imageGallery = new Books();
//            imageGallery.setTitle(title);
//            imageGallery.setImage(imageData);
//            imageGallery.setAuthorName(authorName);
//            imageGallery.setEdition(edition);
//            imageGallery.setFileType("jpg");
//            imageGallery.setDisponibility(true);
//            imageGallery.setCategory(category);
//            imageGallery.setPlace(place);
//            imageGallery.setNbExemplaire(nbExemplaire);
//            imageGallery.setISBN(ISBN);
//            booksService.saveBook(imageGallery);
//            log.info("HttpStatus===" + new ResponseEntity<>(HttpStatus.OK));
//            return new ResponseEntity<>("Product Saved With File - " + fileName, HttpStatus.OK);
//        } catch (Exception e) {
//            e.printStackTrace();
//            log.info("Exception: " + e);
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }


    @PostMapping(path = "book", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Books> createBook(@RequestBody Books livre) {
        Books nouveauLivre = booksService.saveBook(livre);
        return new ResponseEntity<>(nouveauLivre, HttpStatus.CREATED);
    }
//    public void addBook(@RequestBody Books book){
//
//
//        this.booksService.addBook(book);
//    }

//    public @ResponseBody ResponseEntity<String> createBook(@RequestParam("title") String title,
//                                                           @RequestParam("authorName") String authorName,
//                                                           @RequestParam("edition") String edition,
//                                                           @RequestParam("publicationDate") Date publicationDate,
//                                                           @RequestParam("ISBN") String ISBN,
//                                                           @RequestParam("nbExemplaire") int nbExemplaire,
//                                                           @RequestParam("category") Category category,
//                                                           @RequestParam("place") Localisation place,
//                                                           final @RequestParam("image") MultipartFile file,
//                                                           HttpServletRequest request) {
//        try {
//            String uploadDirectory = request.getServletContext().getRealPath(uploadFolder);
//            String fileName = file.getOriginalFilename();
//            String filePath = Paths.get(uploadDirectory, fileName).toString();
//
//            if (fileName == null || fileName.contains("..")) {
//                return new ResponseEntity<>("Sorry! Filename contains invalid path sequence " + fileName, HttpStatus.BAD_REQUEST);
//            }
//
//            byte[] imageData = file.getBytes();
//
//            Optional<Category> optionalCategory = categoryService.findById(category.getId());
//            Optional<Localisation> optionalPlace = localisationService.findById(place.getId());
//
//
//            Books book = new Books();
//            System.out.println("hey me voici, je suis un livre vide"+ book);
//            book.setTitle(title);
//            book.setImage(imageData);
//            book.setAuthorName(authorName);
//            book.setEdition(edition);
//            book.setDisponibility(true);
//            book.setPublicationDate(publicationDate);
//            System.out.println("hey me voici, je suis un livre presque plein"+ book);
//            if (optionalCategory.isPresent() && optionalPlace.isPresent()) {
//                Category categorys = optionalCategory.get();
//                Localisation places = optionalPlace.get();
//
//                // Enregistrement du livre avec les données converties
//                book.setCategory(categorys);
//                book.setPlace(places);
//            }
////            book.setCategory(categorys);
////            book.setPlace(places);
//            book.setNbExemplaire(nbExemplaire);
//            book.setISBN(ISBN);
//            System.out.println("hey me voici, je suis un livre toto"+ book);
//            booksService.saveBook(book);
//
//            return new ResponseEntity<>("Product Saved With File - " + fileName, HttpStatus.OK);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }


}
