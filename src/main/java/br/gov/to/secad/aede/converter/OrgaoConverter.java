package br.gov.to.secad.aede.converter;

import br.gov.to.secad.aede.domain.Orgao;
import br.gov.to.secad.aede.service.OrgaoService;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author alex.santos
 */
@Component(value = "orgaoConverter")
public class OrgaoConverter implements Converter {

    @Autowired
    private OrgaoService orgaoService;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {

        if (!value.trim().equals("")) {
            try {
                return orgaoService.findOrgaoID(Integer.valueOf(value));
            } catch (NumberFormatException e) {
                throw new ConverterException("Elemento Organizacional não encontrado. Mensagem: " + e.getMessage());
            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
        if (value == null || value.equals("")) {
            return "";
        } else {
            return String.valueOf(((Orgao) value).getId());
        }
    }
}
