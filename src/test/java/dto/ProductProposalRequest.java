package dto;

import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductProposalRequest {
	private String name;
	private String language;
	private Category category;
	private List<Parameter> parameters;
	private List<Image> images;
	private Description description;
	
	@Data
	@Builder
	public static class Category{
		private String id;
	}
	
	@Data
	@Builder
	public static class Parameter {
		private String id;
		private List<String> values;
		private List<String> valuesIds;
		private List<String> valuesLabels;
		private String name;
		private String unit;
		private Options options;
		
		@Data
		@Builder
		public static class Options{
			private Boolean identifiesProduct;
			private Boolean isGTIN;
		}
		}
		
	@Data
	@Builder
	public static class Description{
		private List<Section> sections;
	}	
	
	@Data
	@Builder
	public static class Section{
		private List<Item> items;
	}	
	
	@Data
	@Builder
	public static class Item{
		private String type;
		private String content;
	}
	
		
	@Data
	@Builder
	public static class Image {
		private String url;
	}
	public static class ProductProposalRequestBuilder {
		
		public ProductProposalRequest.ProductProposalRequestBuilder withParamValue(String paramId, String validIsbn) {
			
			ProductProposalRequest base = this.build();
			
			List<ProductProposalRequest.Parameter> updatedParameters = new ArrayList<>();
			
			for (var p : base.getParameters()) {
				
				if (!p.getId().equals(paramId)) {
					updatedParameters.add(p);
					continue;
					}
				
				var updatedParam = ProductProposalRequest.Parameter.builder()
						.id(p.getId())
						.name(p.getName())
						.unit(p.getUnit())
						.options(p.getOptions());
						
				if (p.getValues() != null && p.getValuesLabels() != null) {
					updatedParam.values(validIsbn == null? null : List.of(validIsbn));
					updatedParam.valuesLabels(validIsbn == null? null : List.of(validIsbn));
				}
				
				else if (p.getValues() != null && p.getValuesLabels() == null) {
					updatedParam.values(validIsbn == null? null : List.of(validIsbn));
				}
				
				else if (p.getValuesIds() !=null) {
					updatedParam.valuesIds(validIsbn == null? null : List.of(validIsbn));
				}
				
				updatedParameters.add(updatedParam.build());
				
			
			}
			return ProductProposalRequest.builder()
					.name(base.getName())
					.category(base.getCategory())
					.description(base.getDescription())
					.images(base.getImages())
					.language(base.getLanguage())
					.parameters(updatedParameters);
		}
	}
		
}