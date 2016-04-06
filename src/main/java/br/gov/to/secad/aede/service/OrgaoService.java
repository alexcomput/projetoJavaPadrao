/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.to.secad.aede.service;

import br.gov.to.secad.aede.domain.Orgao;
import br.gov.to.secad.aede.repository.IOrgaoRepository;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import static org.springframework.data.jpa.domain.Specifications.where;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author alex.santos
 */
@Service
public class OrgaoService {

    @Autowired
    private IOrgaoRepository iorgaoRepository;

    @Transactional(timeout = 10)
    public Orgao findOrgaoID(Integer idOrgao) {
        try {
            return iorgaoRepository.findOne(idOrgao);
        } catch (Exception e) {
            System.out.println("ERRO: " + e.getLocalizedMessage());
            return null;
        }
    }

    public List<Orgao> findAll() {
        try {
            return iorgaoRepository.findAll(where(especificationEmpresa(1, "I", "empCodigo")));
        } catch (Exception ex) {
            System.err.println(" Erro 78584" + ex.getMessage());
        }
        return null;
    }

    public List<Orgao> findAllOrgaoComissao(Integer orgao) {
        try {
            return iorgaoRepository.findAll(where(especificatioOrgao(1, "I", "empCodigo", "", "")).
                    and(especificatioOrgao(orgao, "O", "comissaoRevisao", "orgao", "id")).
                    and(especificatioOrgao("N", "S", "comissaoRevisao", "excluido", "")));
        } catch (Exception ex) {
            System.err.println(" Erro 78584" + ex.getMessage());
        }
        return null;
    }

    public Specification<Orgao> especificatioOrgao(final Object object, final String tipo, final String fieldCampo, final String filhoField, final String filhoFilhoField) {

        return new Specification<Orgao>() {
            @Override
            public Predicate toPredicate(Root<Orgao> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                if (object != null) {
                    query.distinct(true);
                    if (tipo.equals("I")) {
                        if (!object.equals(0) && !object.equals(null)) {
                            if (!filhoField.equals("")) {
                                if (!filhoFilhoField.equals("")) {
                                    return cb.equal(root.get(fieldCampo).get(filhoField).get(filhoFilhoField), object);
                                } else {
                                    return cb.equal(root.get(fieldCampo).get(filhoField), object);
                                }
                            } else {
                                return cb.equal(root.get(fieldCampo), object);
                            }
                        }
                    } else if (!object.toString().trim().equals("")) {
                        if (!filhoField.equals("")) {
                            if (!filhoFilhoField.equals("")) {
                                return cb.equal(root.get(fieldCampo).get(filhoField).get(filhoFilhoField), object);
                            } else {
                                return cb.equal(root.get(fieldCampo).get(filhoField), object);
                            }
                        } else {
                            return cb.equal(root.get(fieldCampo), object);
                        }
                    }
                }
                return null;
            }
        };
    }

    public Specification<Orgao> especificationEmpresa(final Object object, final String tipo, final String fieldCampo) {

        return new Specification<Orgao>() {
            @Override
            public Predicate toPredicate(Root<Orgao> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                if (object != null) {
                    query.distinct(true);

                    if (tipo.equals("I")) {
                        if (!object.equals(0)) {
                            return cb.equal(root.get(fieldCampo), object);
                        }
                    } else if (!object.toString().trim().equals("")) {
                        return cb.equal(root.get(fieldCampo), object);
                    }
                }
                return null;
            }
        };
    }
}
