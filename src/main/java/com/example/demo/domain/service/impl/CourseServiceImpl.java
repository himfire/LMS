package com.example.demo.domain.service.impl;


import com.example.demo.domain.entity.*;
import com.example.demo.domain.repository.CourseRepository;
import com.example.demo.domain.service.CourseService;
import com.example.demo.domain.service.CourseTagsService;
import com.example.demo.domain.service.UserService;
import com.example.demo.domain.value.dto.CourseDTO;
import com.example.demo.domain.value.dto.LessonModuleDTO;
import com.example.demo.domain.value.dto.TagDTO;
import com.example.demo.domain.value.dto.UserDTO;
import com.example.demo.domain.value.dto.create.CreateCourseDTO;
import com.example.demo.domain.value.dto.update.UpdateCourseDTO;
import com.example.demo.domain.value.dto.update.UpdateCourseTagsDTO;
import com.example.demo.domain.value.enumurator.CourseCategory;
import com.example.demo.domain.value.enumurator.Language;
import com.example.demo.domain.value.enumurator.Level;
import com.example.demo.domain.value.enumurator.TagAction;
import com.example.demo.exceptions.CustomException;
import com.example.demo.utility.validator.UserValidator;
import com.querydsl.core.BooleanBuilder;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@Transactional
@Slf4j
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final CourseTagsService tagService;

    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository, ModelMapper modelMapper, UserService userService, CourseTagsService tagService) {
        this.courseRepository = courseRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.tagService = tagService;
    }
    @Override
    public Page<CourseDTO> getAllCourses(Map<String, Object> filterOption) {
        try {
            QCourse qCourse = QCourse.course;
            BooleanBuilder booleanBuilder = new BooleanBuilder();
            if (filterOption.get("title") != null){
                booleanBuilder.and(qCourse.title.containsIgnoreCase(String.valueOf(filterOption.get("title"))));
            }
            if (filterOption.get("slugTitle") != null){
                booleanBuilder.and(qCourse.slugTitle.containsIgnoreCase(String.valueOf(filterOption.get("slugTitle"))));
            }
            if (filterOption.get("courseCategory") != null){
                booleanBuilder.and(qCourse.courseCategory.eq(CourseCategory.valueOf(String.valueOf(filterOption.get("courseCategory")))));
            }
            if (filterOption.get("skillLevel") != null){
                booleanBuilder.and(qCourse.skillLevel.eq(Level.valueOf(String.valueOf(filterOption.get("skillLevel")))));
            }
            if (filterOption.get("author") != null){
                booleanBuilder.and(qCourse.author.id.eq(Long.valueOf(String.valueOf(filterOption.get("author")))));
            }
//            if (filterOption.get("language") != null){
//                booleanBuilder.and(qCourse.language.eq(Language.valueOf(String.valueOf(filterOption.get("language")))));
//            }

            if (filterOption.get("tags") != null){
                final String[] tagsId = filterOption.get("tags").toString().split(",");
                List<Tag> tags = new ArrayList<>();
                for (String tag : tagsId){
                    tags.add(Tag.builder().id(Long.valueOf(tag)).build());
                }
                booleanBuilder.and(qCourse.tags.any().in(tags));
            }

            if (filterOption.get("level") != null){
                booleanBuilder.and(qCourse.skillLevel.eq(Level.valueOf(String.valueOf(filterOption.get("level")))));
            }
            if (filterOption.get("isActive") != null){
                booleanBuilder.and(qCourse.isActive.eq(
                        Boolean.valueOf(
                                String.valueOf(
                                        filterOption.get("isActive")))));
            }

            if (filterOption.get("minPrice") != null){
                booleanBuilder.and(qCourse.price.goe(Float.parseFloat(String.valueOf(filterOption.get("minPrice")))));
            }
            if (filterOption.get("maxPrice") != null){
                booleanBuilder.and(qCourse.price.loe(Float.parseFloat(String.valueOf(filterOption.get("maxPrice")))));
            }

            String order = (filterOption.get("order") == null) ? "ASC": String.valueOf(filterOption.get("order"));
            int pageNumber = (filterOption.get("page") == null) ? 0 :  Integer.valueOf(String.valueOf(filterOption.get("page")));
            int size = (filterOption.get("size") == null) ? 20 : Integer.valueOf(String.valueOf(filterOption.get("size"))) ;
            Sort.Direction direction = ( order.equals("DESC")) ? Sort.Direction.DESC: Sort.Direction.ASC ;
            PageRequest page = PageRequest.of(pageNumber,size, Sort.by(direction,"id"));
            if (booleanBuilder.getValue() != null) {
                return courseRepository.findAll(booleanBuilder,page).map(course -> modelMapper.map(course, CourseDTO.class));
            }else {
                return courseRepository.findAll(page).map(course -> modelMapper.map(course, CourseDTO.class));
            }

        }catch (Exception e){
            System.out.println(e.getMessage());
            throw CustomException.builder()
                    .code(e.getMessage())
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
        }
    }

    @Override
    public CourseDTO getCourseById(Long id) {
        final Course course = courseRepository.findById(id).orElseThrow(() ->
        CustomException.builder()
                .code("Course not found with id:" + id)
                .status(HttpStatus.BAD_REQUEST)
                .build()
        );
        return modelMapper.map(course,CourseDTO.class);
    }

    @Override
    public Course getById(Long id) {
        return courseRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Course not found with id:"+id));
    }

    public CourseDTO createCourse(CreateCourseDTO dto){
        System.out.println("Author Id: "+dto.getAuthor());
        User courseAuthor = userService.getById(dto.getAuthor());

        final Course course = Course.builder()
                .title(dto.getTitle())
                .slugTitle(dto.getSlugTitle())
                .description(dto.getDescription())
                .skillLevel(dto.getSkillLevel())
                .price(dto.getPrice())
                .duration(dto.getDuration())
                .courseImageURL(dto.getCourseImageURL())
                .courseVideoURL(dto.getCourseVideoURL())
                .author(courseAuthor)
                .language(dto.getLanguage())
//                .contributes(dto.getContributes())
                .build();
        return modelMapper.map(courseRepository.save(course),CourseDTO.class);
    }

    public void deleteCourse(Long id){
        Course classroom = courseRepository.findById(id).orElseThrow(()->
                new EntityNotFoundException("Course not found"));
        courseRepository.delete(classroom);
    }

    public CourseDTO updateCourse(UpdateCourseDTO dto, Long id){
        Course courseToUpdate = courseRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Course not found"));
        courseToUpdate.setTitle(dto.getTitle());
        courseToUpdate.setCourseCategory(dto.getCourseCategory());
        courseToUpdate.setActive(dto.isActive());
        courseToUpdate.setCourseImageURL(dto.getCourseImageURL());
        courseToUpdate.setSkillLevel(dto.getSkillLevel());
        courseToUpdate.setLanguage(dto.getLanguage());
        // TODO: validation + checkUserAccess
        return modelMapper.map(courseToUpdate,CourseDTO.class);
    }

    @Override
    public CourseDTO updateCourseTags(Long courseId, UpdateCourseTagsDTO dto) {
        final Course courseToUpdate = getById(courseId);
        if (dto.getAction().equals(TagAction.ADD)){
            Tag newCourseTag = tagService.getTagById(dto.getTagId());
            courseToUpdate.getTags().add(newCourseTag);
        }else if (dto.getAction().equals(TagAction.REMOVE)){
            courseToUpdate.getTags().remove(tagService.getTagById(dto.getTagId()));
        }
        return modelMapper.map(courseRepository.save(courseToUpdate),CourseDTO.class);
    }

    @Override
    public CourseDTO getCourseBySlugTitle(String slugTitle) {
        final Course course = courseRepository.findBySlugTitle(slugTitle).orElseThrow(() ->
                CustomException.builder()
                        .code("Course not found with title:" + slugTitle)
                        .status(HttpStatus.NOT_FOUND )
                        .build());
        return modelMapper.map(course, CourseDTO.class);
    }
}
