package id.co.edts.ticketservice.utils;

import id.co.edts.apicore.query.SearchCriteria;
import id.co.edts.apicore.query.SearchSpecificationBuilder;
import id.co.edts.apicore.query.dto.CriteriaField;
import id.co.edts.apicore.utils.EntityUtil;
import id.co.edts.basedomain.model.MasterConcert;
import org.springframework.data.jpa.domain.Specification;

public class SearchCriteriaUtil {
    public static Specification<MasterConcert> createSpecification(CriteriaField criteria) {
        SearchSpecificationBuilder<MasterConcert> specificationBuilder =
                criteria.getSearchSpecificationBuilder(EntityUtil.getFilterColumn(MasterConcert.class));

        SearchSpecificationBuilder andSpecification = new SearchSpecificationBuilder(false);

        andSpecification = andSpecification
                .with(new SearchCriteria("isDeleted", false, SearchCriteria.QueryOperator.eq));

        return specificationBuilder.and(andSpecification).build();
    }
}
