package com.br.ApiReme.controller




import com.br.ApiReme.` Port`.entrada.UserPortE
import com.br.ApiReme.domain.UserDomain
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserPortE
) {

    @PostMapping
    fun createUser(@RequestBody user: UserDomain): UserDomain =
        userService.createUser(user)

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: Long): UserDomain? =
        userService.findUserById(id)

    @GetMapping
    fun getAllUsers(): List<UserDomain> =
        userService.findAllUsers()

    @PutMapping("/{id}")
    fun updateUser(@PathVariable id: Long, @RequestBody updatedUser: UserDomain): UserDomain =
        userService.updateUser(id, updatedUser)

    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: Long) {
        userService.deleteUser(id)
    }
}
