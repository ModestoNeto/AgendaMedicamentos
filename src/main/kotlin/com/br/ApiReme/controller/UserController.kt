package com.br.ApiReme.controller




import com.br.ApiReme.` Port`.entrada.UserPortE
import com.br.ApiReme.Dtos.Reponse.User.UserDtoResponse
import com.br.ApiReme.Dtos.Request.User.UserDtoRequest
import com.br.ApiReme.Dtos.Request.User.UserUpdateDtoRequest
import com.br.ApiReme.domain.UserDomain
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserPortE
) {

    @PostMapping
    fun createUser(@RequestBody user: UserDtoRequest): ResponseEntity<UserDtoResponse> {
      val createUser =  userService.createUser(user)
        return ResponseEntity.status(HttpStatus.CREATED).body(createUser)
    }

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: Long): ResponseEntity<UserDtoResponse?> {
       val getUserbyid= userService.findUserById(id)

        return ResponseEntity.status(HttpStatus.OK).body(getUserbyid)

    }

    @GetMapping
    fun getAllUsers(): ResponseEntity<List<UserDtoResponse>> {

        val lisUser =userService.findAllUsers()
        return ResponseEntity.status(HttpStatus.OK).body(lisUser)
    }


    @PutMapping("/{id}")
    fun updateUser(@PathVariable id: Long, @RequestBody updatedUser: UserUpdateDtoRequest): ResponseEntity<UserDtoResponse> {
      val updateEnti =  userService.updateUser(id, updatedUser)

       return ResponseEntity.status(HttpStatus.OK).body(updateEnti)
    }

    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: Long): ResponseEntity<Void> {
        userService.deleteUser(id)
        return ResponseEntity.status(HttpStatus.ACCEPTED).build()
    }
}
