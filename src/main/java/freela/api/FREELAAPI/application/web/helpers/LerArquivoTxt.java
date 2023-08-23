package freela.api.FREELAAPI.application.web.helpers;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import freela.api.FREELAAPI.domain.repositories.CategoryRepository;
import freela.api.FREELAAPI.domain.repositories.SubCategoryRepository;
import freela.api.FREELAAPI.resourses.entities.SubCategory;
import freela.api.FREELAAPI.resourses.entities.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LerArquivoTxt {
    private CategoryRepository categoryRepository;
    private SubCategoryRepository subCategoryRepository;

    public LerArquivoTxt(CategoryRepository repository,SubCategoryRepository subCategoryRepository) {
        this.categoryRepository = repository;
        this.subCategoryRepository = subCategoryRepository;
    }

    public void leArquivo(byte[] arquivo) {
        BufferedReader entrada = null;
        String registro;
        String subCategoryName, categoryName;
        int qtdRegDadosGravados;
        int contaRegDadosLidos = 0;

        List<SubCategory> listaLida = new ArrayList<>();

        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(arquivo)) {
            entrada = new BufferedReader(new InputStreamReader(byteArrayInputStream, StandardCharsets.UTF_8));

            System.out.println("\nLendo e processando o arquivo...");

            registro = entrada.readLine();

            while (registro != null) {
                subCategoryName = registro.substring(2).trim();
                categoryName = registro.substring(0, 2).trim();

                SubCategory subCategory = new SubCategory();
                subCategory.setName(subCategoryName);

                Category category = categoryRepository.findCategoryByName(categoryName);
                if (category == null) {
                    category = new Category();
                    category.setName(categoryName);
                    categoryRepository.save(category);
                }

                subCategory.setCategory(category);

                listaLida.add(subCategory);

                contaRegDadosLidos++;

                registro = entrada.readLine();

                System.out.println(subCategory);
                this.subCategoryRepository.save(subCategory);
            }

        } catch (IOException erro) {
            System.out.println("Erro ao ler o arquivo");
        }

    }
}
