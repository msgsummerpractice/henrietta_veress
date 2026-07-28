// package com.example.spring_rest.service;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertTrue;
// import static org.mockito.Mockito.times;
// import static org.mockito.Mockito.verify;
// import static org.mockito.Mockito.when;

// import java.util.List;
// import java.util.Optional;

// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.junit.jupiter.MockitoExtension;

// import com.example.spring_rest.model.User;
// import com.example.spring_rest.repository.IUserRepository;

// @ExtendWith(MockitoExtension.class)
// public class UserServiceTest {

//     @Mock
//     private IUserRepository userRepository;

//     @InjectMocks
//     private UserService userService;

//     @Test
//     void testCreateUser() {
//         User user = new User(null, "heni", "heniveress@pelda.com", "alma123", "Henrietta", "Veress", null);
//         User savedUser = new User(1L, "heni", "heniveress@pelda.com", "alma123", "Henrietta", "Veress", null);
       
//         when(userRepository.save(user)).thenReturn(savedUser);

//         User result = userService.createUser(user);

//         assertEquals(1L, result.getId());
//         assertEquals("heni", result.getUsername());
//     }

//     @Test
//     void testGetAllUsers() {
//         User user1 = new User(1L, "heni", "heniveress@pelda.com", "alma123", "Henrietta", "Veress", null);
//         User user2 = new User(2l, "marci", "marcik@pelda.com", "korte123", "Marci", "Pek", null);

//         when(userRepository.findAll()).thenReturn(List.of(user1, user2));

//         List<User> result = userService.getAllUsers();

//         assertEquals(2, result.size());
//     }

//     @Test
//     void testGetUserById_found() {
//         User user = new User(1L, "heni", "heniveress@pelda.com", "alma123", "Henrietta", "Veress", null);

//         when(userRepository.findById(1L)).thenReturn(Optional.of(user));

//         Optional<User> result = userService.getUserById(1L);

//         assertTrue(result.isPresent());
//         assertEquals("heni", result.get().getUsername());
//     }

//     @Test
//     void testGetUserById_NotFound() {
//         when(userRepository.findById(15L)).thenReturn(Optional.empty());

//         Optional<User> result = userService.getUserById(15L);

//         assertTrue(result.isEmpty());
//     }
    
//     @Test
//     void testGetByUsername_found() {
//         User user = new User(1L, "heni", "heniveress@pelda.com", "alma123", "Henrietta", "Veress", null);

//         when(userRepository.findByUsername("heni")).thenReturn(Optional.of(user));

//         Optional<User> result = userService.getUserByUsername("heni");

//         assertTrue(result.isPresent());
//         assertEquals("heniveress@pelda.com", result.get().getEmail());
//     }

//     @Test
//     void testGetUserByUsername_notFound() {
//         when(userRepository.findByUsername("none")).thenReturn(Optional.empty());

//         Optional<User> result = userService.getUserByUsername("none");

//         assertTrue(result.isEmpty());
//     }

//     @Test
//     void testGetUserByEmail() {
//         User user = new User(1L, "heni", "heniveress@pelda.com", "alma123", "Henrietta", "Veress", null);

//         when(userRepository.findbyEmail("heniveress@pelda.com")).thenReturn(Optional.of(user));

//         Optional<User> result = userService.getUserByEmail("heniveress@pelda.com");

//         assertTrue(result.isPresent());
//         assertEquals("heni", result.get().getUsername());
//     }

//     @Test
//     void testUpdateUser() {
//         User updatedData = new User(null, "heni_uj", "heniuj@pelda.com", "ujalma123", "Henrietta", "Veress", );
//         User savedUser = new User(1L, "heni_uj", "heniuj@pelda.com", "ujalma123", "Henrietta", "Veress");

//         when(userRepository.save(updatedData)).thenReturn(savedUser);

//         User result = userService.updateUser(1L, updatedData);

//         assertEquals(1L, result.getId());
//         assertEquals("heni_uj", result.getUsername());
//     }

//     @Test
//     void testDeleteUser() {
//         userService.deleteUser(1L);

//         verify(userRepository, times(1)).deleteById(1L);
//     }
// }
