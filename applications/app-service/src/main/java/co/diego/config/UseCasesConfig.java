package co.diego.config;

import co.diego.model.flower.gateways.FlowerGateway;
import co.diego.usecase.deleteflower.DeleteFlowerUseCase;
import co.diego.usecase.getallflowers.GetAllFLowersUseCase;
import co.diego.usecase.getflowerbyid.GetFLowerByIDUseCase;
import co.diego.usecase.saveflower.SaveFlowerUseCase;
import co.diego.usecase.updateflower.UpdateFlowerUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(basePackages = "co.diego.usecase")
/*@ComponentScan(basePackages = "co.diego.usecase",
        includeFilters = {
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = "^.+UseCase$")
        },
        useDefaultFilters = false)*/
public class UseCasesConfig {

        @Bean
        public GetAllFLowersUseCase getAllFLowersUseCase(FlowerGateway gateway){
                return new GetAllFLowersUseCase(gateway);
        }

        @Bean
        public GetFLowerByIDUseCase getFLowerByIDUseCase(FlowerGateway gateway){
                return new GetFLowerByIDUseCase(gateway);
        }

        @Bean
        public SaveFlowerUseCase saveFlowerUseCase(FlowerGateway gateway){
                return new SaveFlowerUseCase(gateway);
        }

        @Bean
        public UpdateFlowerUseCase updateFlowerUseCase(FlowerGateway gateway){
                return new UpdateFlowerUseCase(gateway);
        }

        @Bean
        public DeleteFlowerUseCase deleteFlowerUseCase(FlowerGateway gateway){
                return new DeleteFlowerUseCase(gateway);
        }
}
