package com.example.client;
import com.example.auth.AuthenticationRequest;
import com.example.auth.AuthenticationResponse;
import com.example.auth.AuthenticationService;
import com.example.auth.RegisterRequest;
import com.example.token.Token;
import com.example.user.User;
import com.example.user.UserRepository;
import org.springframework.http.ResponseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class AppController {

    private final AuthenticationService authService;
    private final ClientService clientService;
    private final UserRepository userRepository;

//    @GetMapping("/users") // создаем контроллер по добавлению студента
//    public String showUsers(Model model) {
//        return "user_list";
//    }

    @PostMapping("/auth/registered")
    public ModelAndView register(@ModelAttribute("regRequest") RegisterRequest request) {
        ModelAndView mav = new ModelAndView("redirect:/");
        mav.addObject("result", ResponseEntity.ok(authService.register(request))); // add any additional objects you want to pass to the HTML page
        return mav;
    }
//    @PostMapping("/auth/register")
//    public ResponseEntity<AuthenticationResponse> register(
//             RegisterRequest request
//    ) {
//        return ResponseEntity.ok(authService.register(request));
//    }

    @PostMapping("/authenticate")
    public String authenticate(AuthenticationRequest request, Model model) {
        // Authenticate the user and retrieve the token
        ResponseEntity<AuthenticationResponse> responseEntity = authService.authenticate(request);
        AuthenticationResponse response = responseEntity.getBody();
        String token = response.getToken();

        // Add the token as an attribute to the model
        model.addAttribute("token", token);

        // Redirect to the index page
        return "redirect:/";
    }
    @PostMapping("/auth/authenticated")
    public ModelAndView authenticate(AuthenticationRequest request) {
        ModelAndView mav = new ModelAndView("redirect:/");
        mav.addObject("token", ResponseEntity.ok(authService.authenticate(request)));
        return mav;
    }

//    @PostMapping("/auth/authenticate")
//    public String authenticate(Model model, AuthenticationRequest request) {
////        ModelAndView mav = new ModelAndView("index");
//        model.addAttribute("token", ResponseEntity.ok(authService.authenticate(request)));
//        return "";
//    }
//    @PostMapping("/auth/authenticated")
//    public ResponseEntity<AuthenticationResponse> authenticate(
//             AuthenticationRequest request
//    ) {
//        return ResponseEntity.ok(authService.authenticate(request));
//    }

    @GetMapping("/")
    public String viewHomePage(Model model, @Param("keyword") String keyword) {
        List<Client> listClients = clientService.listAll(keyword);
        model.addAttribute("authenticationRequest", new AuthenticationRequest());
        model.addAttribute("listClients", listClients);
        model.addAttribute("keyword", keyword);
        return "index";
    }

    @RequestMapping("/new") // создаем контроллер по добавлению студента
    public String showNewPerformanceForm(Model model) {
        Client client = new Client();
        model.addAttribute("client", client);
        return "new_client";
    }
    @RequestMapping(value = "/save", method = RequestMethod.POST) // POST submits data to be processed to the identified resource.
    // requests a representation of the specified resource
    public String saveCar(@ModelAttribute("client") Client client) { // метод сохранения студентов
        clientService.save(client);
        return "redirect:/";
    }
    @RequestMapping("/edit/{id}") // контроллер для редактирования студентов по ключу
    public ModelAndView showEditClientForm(@PathVariable(name="id") Long id) { // Annotation which indicates that a method parameter should be bound (связано) to a URI template variable
        ModelAndView mav = new ModelAndView("edit_client");
        Client client = clientService.get(id);
        mav.addObject("Client", client);
        return mav; // возвращаем страницу с данными о студентах по id
    }
    @RequestMapping("/delete/{id}")
    public String deleteClient(@PathVariable(name = "id") Long id) {
        clientService.delete(id);
        return "redirect:/";
    }
    @GetMapping("/auth/authenticate") // is a composed annotation that acts as a shortcut for @RequestMapping(method = RequestMethod.GET).
    public String showLogin() {// add any additional objects you want to pass to the HTML page
        return "login";
//        return "redirect:/login";
    }
//    @PostMapping("/auth/authenticate")
//    public String SuccessLogin() {
//        return "redirect:/";
//    }
    @GetMapping("/auth/register")
    public ModelAndView showRegistrationForm() {
        ModelAndView mav = new ModelAndView("register");
        mav.addObject("register"); // add any additional objects you want to pass to the HTML page
        return mav;
    }
    @GetMapping("/auth/logout")
    public ModelAndView showLogoutForm() {
        ModelAndView mav = new ModelAndView("login");
        mav.addObject("login"); // add any additional objects you want to pass to the HTML page
        return mav;
    }
//    @GetMapping("/auth/logout")
//    public ModelAndView showRegisterForm() {
//        ModelAndView mav = new ModelAndView("register");
//        mav.addObject("register"); // add any additional objects you want to pass to the HTML page
//        return mav;
//    }
//    @GetMapping("/admin/users")
//    public String showUserForm(Model model) {
//        model.addAttribute("user", new User());
//        return "user_form";
//    }
//    @PostMapping("/admin/users")
//    public String addUser(@ModelAttribute("user") User user) {
//        userRepository.save(user);
//        return "redirect:/users";
//    }
//    @PostMapping("/users")
//    public String saveUser(@ModelAttribute("user") User user) {
//        userService.save(user);
//        return "redirect:/users";
//    }
//    @RequestMapping("/delete_user")
//    public String deleteUser(@PathVariable(name = "id") String username) {
//        userService.deleteByUsername(username);
//        return "redirect:/users";
//    }
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private BCryptPasswordEncoder bCryptPasswordEncoder;

//    @GetMapping("/register")
//    public String showRegistrationForm(Model model) {
//        model.addAttribute("user", new User());
//        return "register";
//    }


//    @PostMapping("/register")
//    public String registerUser(@Validated(User.ValidationStepOne.class) User user, BindingResult bindingResult, Model model) {
//        if (bindingResult.hasErrors()) {
//            return "register";
//        }
//        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
//        userRepository.save(user);
//        return "redirect:/login";
//    }
//    @PostMapping("/register")
//    public String submitRegistrationForm(@RequestParam("username") String username,
//                                         @RequestParam("password") String password,
//                                         Model model) {
//
//        // Here you would save the user's information to a database or perform some other action
//
//        model.addAttribute("message", "Registration successful!");
//        return "register";
//    }
//    @PostMapping("/register")
//    public String addUser(@ModelAttribute("user") UserForm userForm) {
//        UserDetails newUser = User.withDefaultPasswordEncoder()
//                .username(userForm.getUsername())
//                .password(userForm.getPassword())
//                .roles("USER")
//                .build();
//        InMemoryUserDetailsManager userDetailsManager =
//                new InMemoryUserDetailsManager(newUser);
//        userDetailsManager.createUser(newUser);
//        return "redirect:/register";
//    }

}


