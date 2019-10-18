package com.lambdaschool.starthere.controllers;

import com.lambdaschool.starthere.models.ErrorDetail;
import com.lambdaschool.starthere.models.Student;
import com.lambdaschool.starthere.services.StudentService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {
    private static final Logger logger = LoggerFactory.getLogger( StudentController.class );
    @Autowired
    private StudentService studentService;

    // Please note there is no way to add students to course yet!

    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "Number of records per page."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "Sorting criteria in the format: property(,asc|desc). " +
                            "Default sort order is ascending. " +
                            "Multiple sort criteria are supported.")})


//localhost:2085/students/students
//localhost:2085/students/student/paging/?page1&size=10
//localhost:2085/students/student/paging/?sort=studname,desc
    //these are implicit because they're a part of the pagingandlogging repo
    @ApiOperation(value = "Retrieves all of the students", response = Student.class, responseContainer = "List")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Students Found", response = Student.class),
            @ApiResponse(code = 404, message = "Students Not Found", response = ErrorDetail.class)})
    @GetMapping(value = "/students", produces = {"application/json"})
    public ResponseEntity<?> listAllStudents() {
        logger.info( "GET endpoint /students has been accessed" );
        List<Student> myStudents = studentService.findAll();
        return new ResponseEntity<>( myStudents, HttpStatus.OK );
    }

    @ApiOperation(value = "Returns a number of students by page", response = Student.class, responseContainer = "List")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Students Found", response = Student.class),
            @ApiResponse(code = 404, message = "Students Not Found", response = ErrorDetail.class)})
    @GetMapping(value = "/student/paging", produces = {"application/json"})
    public ResponseEntity<?> listAllStudentsByPage(@PageableDefault(page = 0, size = 3) Pageable pageable) {
        List<Student> myStudents = studentService.findAllPageable( pageable );
        return new ResponseEntity<>( myStudents, HttpStatus.OK );
    }

    @ApiOperation(value = "Returns a student by ID", response = Student.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Student ID Found", response = Student.class),
            @ApiResponse(code = 404, message = "Student ID Not Found", response = ErrorDetail.class)})
    @GetMapping(value = "/Student/{StudentId}",
            produces = {"application/json"})
    public ResponseEntity<?> getStudentById(
            @ApiParam(value = "Student's ID number", required = true, example = "3")
            @PathVariable
                    Long StudentId) {
        logger.info( "GET endpoint /Student/" + StudentId + " has been accessed" );
        Student r = studentService.findStudentById( StudentId );
        return new ResponseEntity<>( r, HttpStatus.OK );
    }

    @ApiOperation(value = "Returns a student partial name", response = Student.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Student Found", response = Student.class),
            @ApiResponse(code = 404, message = "Student Not Found", response = ErrorDetail.class)})
    @GetMapping(value = "/student/namelike/{name}",
            produces = {"application/json"})
    public ResponseEntity<?> getStudentByNameContaining(
            @ApiParam(value = "Partial student's name", required = true, example = "mar")

            @PathVariable String name) {
        logger.info( "GET endpoint /student/namelike/" + name + " accessed" );
        List<Student> myStudents = studentService.findStudentByNameLike( name );
        return new ResponseEntity<>( myStudents, HttpStatus.OK );
    }

    @ApiOperation(value = "Adds a Student", response = Student.class)
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Student Added", response = Student.class),
            @ApiResponse(code = 404, message = "Student not added", response = ErrorDetail.class)})
    @PostMapping(value = "/Student",
            consumes = {"application/json"},
            produces = {"application/json"})
    public ResponseEntity<?> addNewStudent(@Valid
                                           @RequestBody
                                                   Student newStudent) throws URISyntaxException {
        logger.info( "POST endpoint /Student has been accessed" );
        newStudent = studentService.save( newStudent );

        // set the location header for the newly created resource
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newStudentURI = ServletUriComponentsBuilder.fromCurrentRequest().path( "/{Studentid}" ).buildAndExpand( newStudent.getStudid() ).toUri();
        responseHeaders.setLocation( newStudentURI );

        return new ResponseEntity<>( null, responseHeaders, HttpStatus.CREATED );
    }

    @ApiOperation(value = "Updates Student", response = Student.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Student Updated", response = Student.class),
            @ApiResponse(code = 404, message = "Student not updated", response = ErrorDetail.class)})
    @PutMapping(value = "/Student/{Studentid}")
    public ResponseEntity<?> updateStudent(
            @RequestBody
                    Student updateStudent,
            @PathVariable
                    long Studentid) {
        logger.info( "PUT endpoint /Student/" + Studentid + " has been accessed" );
        studentService.update( updateStudent, Studentid );
        return new ResponseEntity<>( HttpStatus.OK );
    }

    @ApiOperation(value = "Deletes a student by ID", response = Student.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Student Deleted", response = Student.class),
            @ApiResponse(code = 404, message = "Student not deleted", response = ErrorDetail.class)})

    @DeleteMapping("/Student/{Studentid}")
    public ResponseEntity<?> deleteStudentById(
            @PathVariable
                    long Studentid) {
        logger.info( "DELETE /Student/" + Studentid + " accessed" );
        studentService.delete( Studentid );
        return new ResponseEntity<>( HttpStatus.OK );
    }

}