package com.bikkadIt.ElectronicStore.Controlller;

import com.bikkadIt.ElectronicStore.dtos.ApiResponseMessage;
import com.bikkadIt.ElectronicStore.dtos.ImageResponse;
import com.bikkadIt.ElectronicStore.dtos.PageableResponse;
import com.bikkadIt.ElectronicStore.dtos.ProductDto;
import com.bikkadIt.ElectronicStore.service.FileService;
import com.bikkadIt.ElectronicStore.service.ProductService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private FileService fileService;
    @Value("${product.image.path}")
    private String imagePath;

    //create
    @PostMapping
    public ResponseEntity<ProductDto> crateProduct(@RequestBody ProductDto productDto) {
        ProductDto createProduct = productService.create(productDto);
        return new ResponseEntity<>(createProduct, HttpStatus.CREATED);
    }

    //update
    @PutMapping("/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto productDto, String productId) {
        ProductDto updateProduct = productService.update(productDto, productId);
        return new ResponseEntity<>(updateProduct, HttpStatus.OK);
    }

    //delete
    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponseMessage> delete(@PathVariable String productId) {
        productService.delete(productId);
        ApiResponseMessage responseMessage = ApiResponseMessage.builder()
                .message("product is deleted")
                .status(HttpStatus.OK).success(true).build();

        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    //get single
    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable String productId) {
        ProductDto productDto = productService.get(productId);
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }

    //get all
    @GetMapping
    public ResponseEntity<PageableResponse<ProductDto>> getAll(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortedBy", defaultValue = "title", required = false) String sortedBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {

        PageableResponse<ProductDto> pageableResponse = productService.getAll(pageNumber, pageSize, sortedBy, sortDir);
        return new ResponseEntity<>(pageableResponse, HttpStatus.OK);
    }

    //get all live
    //  /product/live
    @GetMapping("/live")
    public ResponseEntity<PageableResponse<ProductDto>> getAllLive(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortedBy", defaultValue = "title", required = false) String sortedBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {

        PageableResponse<ProductDto> pageableResponse = productService.getAllLive(pageNumber, pageSize, sortedBy, sortDir);
        return new ResponseEntity<>(pageableResponse, HttpStatus.OK);
    }

    //get search0
    @GetMapping("/search/{query}")
    public ResponseEntity<PageableResponse<ProductDto>> searchProduct(
            @PathVariable String query,
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortedBy", defaultValue = "title", required = false) String sortedBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {

        PageableResponse<ProductDto> pageableResponse = productService.searchByTitle(query, pageNumber, pageSize, sortedBy, sortDir);
        return new ResponseEntity<>(pageableResponse, HttpStatus.OK);
    }

    //upload image
    @PostMapping("/image/{productId}")
    public ResponseEntity<ImageResponse> uploadProductImage(
            @PathVariable String productId,
            @RequestParam("productImage") MultipartFile image
    ) throws IOException {
        String fileName = fileService.uploadFile(image, imagePath);
        ProductDto productDto = productService.get(productId);
        productDto.setProductImageName(fileName);
        ProductDto updatedProduct = productService.update(productDto, productId);

        ImageResponse response = ImageResponse.builder()
                .imageName(updatedProduct.getProductImageName())
                .message("Product image is successfully uploaded !!")
                .status(HttpStatus.CREATED).success(true).build();

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    //serve image
    @GetMapping(value = "/image/{productId}")
    public void serveUserImage(@PathVariable String productId, HttpServletResponse response) throws IOException {
        ProductDto productDto = productService.get(productId);

        InputStream resource = fileService.getResource(imagePath, productDto.getProductImageName());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());

    }


}
