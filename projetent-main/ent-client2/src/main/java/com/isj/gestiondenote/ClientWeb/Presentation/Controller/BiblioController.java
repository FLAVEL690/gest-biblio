package com.isj.gestiondenote.ClientWeb.Presentation.Controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.isj.gestiondenote.ClientWeb.Model.dto.BookDto;
import com.isj.gestiondenote.ClientWeb.Model.dto.CategoryDto;
import com.isj.gestiondenote.ClientWeb.Model.dto.LocalisationDto;
import com.isj.gestiondenote.ClientWeb.Model.entities.Book;
import com.isj.gestiondenote.ClientWeb.Model.entities.Category;
import com.isj.gestiondenote.ClientWeb.utils.test.Modal;
import com.isj.gestiondenote.ClientWeb.utils.test.ModalWithHttpHeader;
import com.isj.gestiondenote.ClientWeb.utils.test.URL;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;

@Slf4j
@Controller
public class BiblioController {
    @GetMapping("/gestionbibliotheque")
    public String pageBibliotheque(Model model, HttpSession session){
        ModalWithHttpHeader.model(model, session);
        return "pages/gestion-bibliotheque/AcceuilBiblio";
    }

    @GetMapping("/Listelivres")
    public ModelAndView listelivre(Model model, HttpSession session) {
        ModalWithHttpHeader.model(model, session);
        Modal.model(model);
        String accessToken = (String) session.getAttribute("access_token");
        model.addAttribute("accessToken", accessToken);
        RestTemplate restTemplate = new RestTemplate();
        Object[] book = restTemplate.getForObject(URL.BASE_URL_BIBLIO+"book", Object[].class);
        model.addAttribute("book", book);
        return new ModelAndView("pages/gestion-bibliotheque/book");
    }
    @GetMapping("/Listelivresetu/details")
    public ModelAndView pageBibliothequedetail(@PathVariable Integer id ,Model model, HttpSession session){
        ModalWithHttpHeader.model(model, session);
        Modal.model(model);
        String accessToken = (String) session.getAttribute("access_token");
        model.addAttribute("accessToken", accessToken);
        RestTemplate restTemplate = new RestTemplate();
        Object[] book = restTemplate.getForObject(URL.BASE_URL_BIBLIO+"book/"+ id, Object[].class);
        model.addAttribute("book", book);
        return new ModelAndView("pages/gestion-bibliotheque/detail");
    }
    @GetMapping("/Liste/{id}")
    public ModelAndView detailListe(Model model, HttpSession session, @PathVariable String id) {
        System.out.println("test 1"+ session.getAttribute("accessToken"));
        ModalWithHttpHeader.model(model, session);
        Modal.model(model);
        String accessToken = (String) session.getAttribute("accessToken");
        model.addAttribute("accessToken", accessToken);
        RestTemplate restTemplate = new RestTemplate();
        System.out.println("test 4"+ session.getAttribute("accessToken"));
        String url = URL.BASE_URL_BIBLIO+"book/" + id;
        System.out.println("test 2"+ url);
        Object books = restTemplate.getForObject(url, Object.class);
        System.out.println("test 5"+ session.getAttribute("access_token"));
        System.out.println(books + "hello je suis un livre");
        model.addAttribute("books", books);
        System.out.println("test 6"+ session.getAttribute("access_token"));
        return new ModelAndView("pages/gestion-bibliotheque/detail");
    }

    @GetMapping("/Listelivresetu")
    public ModelAndView listelivretu(Model model, HttpSession session) {
        ModalWithHttpHeader.model(model, session);
        Modal.model(model);
        String accessToken = (String) session.getAttribute("access_token");
        model.addAttribute("accessToken", accessToken);
        RestTemplate restTemplate = new RestTemplate();
        Object[] book = restTemplate.getForObject(URL.BASE_URL_BIBLIO+"book", Object[].class);
        model.addAttribute("book", book);
        return new ModelAndView("pages/gestion-bibliotheque/biblioetudiant");
    }


    @GetMapping("/Listeplace")
    public ModelAndView listeplace(Model model, HttpSession session) {
        ModalWithHttpHeader.model(model, session);
        Modal.model(model);
        String accessToken = (String) session.getAttribute("access_token");
        model.addAttribute("accessToken", accessToken);
        RestTemplate restTemplate = new RestTemplate();
        Object[] localisation = restTemplate.getForObject(URL.BASE_URL_BIBLIO+"localisation", Object[].class);
        model.addAttribute("localisation", localisation);
        return new ModelAndView("pages/gestion-bibliotheque/view_place");
    }

    @GetMapping("/deletecategory/{id}")
    public String deletecategory(@PathVariable Integer id, Model model) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.delete(URL.BASE_URL_BIBLIO + "category/" + id );
            System.out.println(restTemplate);
            return "redirect:/Listecategory";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/Listecategory";
    }

    }
    @GetMapping("/deletelocalisation/{id}")
    public String deletelocalisation(@PathVariable Integer id, Model model) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.delete(URL.BASE_URL_BIBLIO + "localisation/" + id );
            System.out.println(restTemplate);
            return "redirect:/Listeplace";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/Listeplace";
        }

    }

//    @GetMapping("/Addbookold")
//    public String addbook(Model model, HttpSession session){
//        ModalWithHttpHeader.model(model, session);
//        Modal.model(model);
//        return "pages/gestion-bibliotheque/addbook";
//    }

    @GetMapping("/Addbook")
    public ModelAndView addBook(Model model, HttpSession session) {
        ModalWithHttpHeader.model(model, session);
        Modal.model(model);
        String accessToken = (String) session.getAttribute("access_token");
        model.addAttribute("accessToken", accessToken);
        RestTemplate restTemplate = new RestTemplate();
        Book book = new Book();
        model.addAttribute("book", book);
        Object category = restTemplate.getForObject(URL.BASE_URL_BIBLIO+"category", Object.class);
        model.addAttribute("category", category);
        Object place = restTemplate.getForObject(URL.BASE_URL_BIBLIO+"localisation", Object.class);
        model.addAttribute("place", place);
        return new ModelAndView("pages/gestion-bibliotheque/addbook");
    }


    @PostMapping("/formBook")
    @JsonIgnoreProperties(value = { "image" })
    public String creerBook(@ModelAttribute BookDto categorie, Model model) throws URISyntaxException {
        try {
            System.out.println("je suis un livre11111111"+ categorie);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            RestTemplate restTemplate = new RestTemplate();
            System.out.println("je suis un livre2222222222"+ categorie);
            HttpEntity<BookDto> httpEntity = new HttpEntity<>(categorie, headers);
            System.out.println(categorie);
            System.out.println("je suis un livre333333333333"+ httpEntity);
            Object[] category = restTemplate.postForObject(new URI(URL.BASE_URL_BIBLIO + "book"), httpEntity, Object[].class);
            System.out.println("je suis un livre44444444444444444"+ category);
            return "redirect:/Listelivres";
        }catch(Exception e) {
            e.printStackTrace();
            return "redirect:/Listelivres";
        }

    }
//    public String creerBook(@ModelAttribute BookDto book, Model model) throws URISyntaxException {
//        try {
//            System.out.println("11111111111111111111111111111"+book);
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
//            System.out.println("2222222222222222222222222222222" + book  );
//            RestTemplate restTemplate = new RestTemplate();
//
//            MultiValueMap<String, Object> body = new LinkedMultiValueMap<String, Object>();
//
////            body.add("image", book.getImage());
//            body.add("ISBN", book.getISBN());
//            body.add("title", book.getTitle());
//            body.add("authorName", book.getAuthorName());
//            body.add("edition", book.getEdition());
//            body.add("publicationDate", book.getPublicationDate());
//            body.add("nbExemplaire", book.getNbExemplaire());
//            body.add("place", book.getPlace());
//            body.add("category", book.getCategory());
//            body.add("disponibility", book.getDisponibility());
//
//
//            HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(body, headers);
//            System.out.println("3333333333333333333333333333333333"+body );
//
//            Object category = restTemplate.postForEntity(URL.BASE_URL_BIBLIO + "book",httpEntity, Object.class);
//            System.out.println("44444444444444444444444444444444"+category  );
//            return "redirect:/Listelivres";
//        }catch(Exception e) {
//            e.printStackTrace();
//            return "redirect:/Listelivres";
//        }
//
//    }


    @GetMapping("/AddCat")
    public String addcat(Model model, HttpSession session){
        ModalWithHttpHeader.model(model, session);
        Modal.model(model);
        return "pages/gestion-bibliotheque/addcategory";
    }
    @GetMapping("/Viewemprunt")
    public String viewemprunt(Model model, HttpSession session){
        ModalWithHttpHeader.model(model, session);
        Modal.model(model);
        return "pages/gestion-bibliotheque/emprunview";
    }
    @GetMapping("/Emprunt")
    public String emprunt2(Model model, HttpSession session){
        ModalWithHttpHeader.model(model, session);
        Modal.model(model);
        return "pages/gestion-bibliotheque/emprunt";
    }
//    @GetMapping("/Demandes")
//    public String askloan(Model model, HttpSession session){
//        ModalWithHttpHeader.model(model, session);
//        Modal.model(model);
//        return "pages/gestion-bibliotheque/demande";
//    }

    @GetMapping("/Demande")
    public ModelAndView askloan(Model model, HttpSession session) {
        ModalWithHttpHeader.model(model, session);
        Modal.model(model);
        String accessToken = (String) session.getAttribute("access_token");
        model.addAttribute("accessToken", accessToken);
        RestTemplate restTemplate = new RestTemplate();
        Object[] book = restTemplate.getForObject(URL.BASE_URL_BIBLIO + "book", Object[].class);
        model.addAttribute("book", book);
        return new ModelAndView("pages/gestion-bibliotheque/demande");
    }
    @PostMapping("/formCategory")
    public String creerCategorie(@ModelAttribute CategoryDto categorie, Model model) throws URISyntaxException {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            RestTemplate restTemplate = new RestTemplate();

            HttpEntity<CategoryDto> httpEntity = new HttpEntity<>(categorie, headers);
            System.out.println(categorie);

            Object[] category = restTemplate.postForObject(new URI(URL.BASE_URL_BIBLIO + "category"), httpEntity, Object[].class);
            return "redirect:/Listecategory";
        }catch(Exception e) {
            e.printStackTrace();
            return "redirect:/Listecategory";
        }

    }

    @GetMapping("/Addplace")
    public String addplace(Model model, HttpSession session){
        ModalWithHttpHeader.model(model, session);
        Modal.model(model);
        return "pages/gestion-bibliotheque/addplace";
    }

    @PostMapping("/formPlace")
    public String creerPlace(@ModelAttribute LocalisationDto loc, Model model) throws URISyntaxException {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            RestTemplate restTemplate = new RestTemplate();

            HttpEntity<LocalisationDto> httpEntity = new HttpEntity<>(loc, headers);
            System.out.println(loc);

            Object[] category = restTemplate.postForObject(new URI(URL.BASE_URL_BIBLIO + "localisation"), httpEntity, Object[].class);
            return "redirect:/Listeplace";
        }catch(Exception e) {
            e.printStackTrace();
            return "redirect:/Listeplace";
        }

    }
}
