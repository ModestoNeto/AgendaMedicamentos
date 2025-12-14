package com.br.ApiReme.controller




import com.br.ApiReme.` Port`.entrada.UserPortE
import com.br.ApiReme.Dtos.Reponse.User.UserDtoResponse
import com.br.ApiReme.Dtos.Request.User.UserDtoRequest
import com.br.ApiReme.Dtos.Request.User.UserUpdateDtoRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@Tag(name = "Users", description = "Gerenciamento de usuários do sistema")
@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserPortE

) {

    @Operation(summary = "Criar um novo usuário")
    @ApiResponses(
        ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
        ApiResponse(responseCode = "400", description = "Dados inválidos")
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    fun createUser(@RequestBody user: UserDtoRequest): ResponseEntity<UserDtoResponse> {
      val createUser =  userService.createUser(user)
        return ResponseEntity.status(HttpStatus.CREATED).body(createUser)
    }



    @Operation(summary = "Buscar usuário por ID")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Usuário encontrado"),
        ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    )
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: Long): ResponseEntity<UserDtoResponse?> {
       val getUserbyid= userService.findUserById(id)

        return ResponseEntity.status(HttpStatus.OK).body(getUserbyid)

    }


    @Operation(summary = "Listar todos os usuários")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    )
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    fun getAllUsers(): ResponseEntity<List<UserDtoResponse>> {

        val lisUser =userService.findAllUsers()
        return ResponseEntity.status(HttpStatus.OK).body(lisUser)
    }



    @Operation(summary = "Atualizar um usuário")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
        ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    fun updateUser(@PathVariable id: Long, @RequestBody updatedUser: UserUpdateDtoRequest): ResponseEntity<UserDtoResponse> {
      val updateEnti =  userService.updateUser(id, updatedUser)

       return ResponseEntity.status(HttpStatus.OK).body(updateEnti)
    }




    @Operation(summary = "Excluir um usuário")
    @ApiResponses(
        ApiResponse(responseCode = "204", description = "Usuário excluído com sucesso"),
        ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    )
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: Long): ResponseEntity<Void> {
        userService.deleteUser(id)
        return ResponseEntity.status(HttpStatus.ACCEPTED).build()
    }

}
