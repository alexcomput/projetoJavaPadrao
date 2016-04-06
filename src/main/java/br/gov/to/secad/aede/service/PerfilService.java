/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.to.secad.aede.service;

import br.gov.to.secad.aede.domain.Perfil;
import br.gov.to.secad.aede.repository.IPerfilRepository;
import br.gov.to.secad.aede.view.LoginController;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import static org.springframework.data.jpa.domain.Specifications.where;

/**
 *
 * @author alex.santos
 */
@Service
public class PerfilService {

    @Autowired
    IPerfilRepository iPerfilRepository;

    @Transactional(timeout = 10)
    public List<Perfil> listarPerfil() {
        try {
            return iPerfilRepository.findAll();
        } catch (Exception e) {
            System.out.println("ERRO: " + e.getLocalizedMessage());
            return null;
        }
    }

    @Transactional(timeout = 10)
    public List<Perfil> listarPerfilSemAdministrativo() {
        try {
            // Retorna todos os perfils EXCETO O ADMINISTRADOR 
             //iPerfilRepository.findAll(especificationPerfilParametros(1, "I", "id", false));
                if (LoginController.getCurrentInstance().getPermissaoAtual().getPerfil().getId() != 1) {
                return iPerfilRepository.findAll(where(especificationPerfilGeneric(1, "I", "id", false)));
            } else {
                return iPerfilRepository.findAll();
            }

        } catch (Exception e) {
            System.out.println("ERRO: " + e.getLocalizedMessage());
            return null;
        }
    }

     
    @Transactional(timeout = 10)
    public Perfil findPerfilID(Integer idPerfil) {
        try {
            return iPerfilRepository.findOne(idPerfil);
        } catch (Exception e) {
            System.out.println("ERRO: " + e.getLocalizedMessage());
            return null;
        }
    }

    public Perfil findOnePerfil(Integer codigo) {
        return iPerfilRepository.findOne(codigo);
    }

    public boolean salvar(Perfil perfil) {
        try {
            iPerfilRepository.save(perfil);
            return true;
        } catch (Exception e) {
            System.out.println("Erro:" + e.getLocalizedMessage());
            return false;
        }
    }

    public boolean remover(Perfil perfil) {
        try {
            iPerfilRepository.delete(perfil);
            return true;
        } catch (Exception ex) {
            System.out.println("ERro: " + ex.getLocalizedMessage());
            return false;
        }
    }

    public Specification<Perfil> especificationPerfilParametros(final Object object, final String tipo, final String fieldCampo, final boolean igual) {

        return new Specification<Perfil>() {
            @Override
            public Predicate toPredicate(Root<Perfil> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                /*
                 Confere se o objeto é diferente de NULL                 */
                if (object != null) {
                    /*
                     Depois confere se o mesmo é DO TIPO INTEIRO
                     */
                    query = cb.createTupleQuery(); //tried cb.createQuery(MyInfo.class); as well

                    List<Selection<? extends Object>> selectionList = new ArrayList<Selection<? extends Object>>();

                    Selection<? extends Object> selection = root.get("descricao");
                    selectionList.add(selection);

                    //query.distinct(true);
                    return query.multiselect(selectionList).where(cb.notEqual(root.get(fieldCampo), object)).getRestriction();

                }
                return null;
            }

        };
    }

    public Specification<Perfil> especificationPerfilGeneric(final Object object, final String tipo, final String fieldCampo, final boolean igual) {

        return new Specification<Perfil>() {
            @Override
            public Predicate toPredicate(Root<Perfil> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                /*
                 Confere se o objeto é diferente de NULL                 */
                if (object != null) {
                    /*
                     Depois confere se o mesmo é DO TIPO INTEIRO
                     */
                    query.distinct(true);
                    if (tipo.equals("I")) {
                        // confere se o objeto é dirente de Zero
                        if (!object.equals(0)) {
                            //Comparacao de igualdade se for verdadeiro e diferente se for false
                            if (igual) {
                                return cb.equal(root.get(fieldCampo), object);
                            } else {
                                return cb.notEqual(root.get(fieldCampo), object);

                            }

                        }
                    } else if (!object.toString().trim().equals("")) {
                        return cb.equal(root.get(fieldCampo), object);
                    }
                }
                System.out.println(" valor do objeto " + object);

                return null;
            }
        };
    }
}
