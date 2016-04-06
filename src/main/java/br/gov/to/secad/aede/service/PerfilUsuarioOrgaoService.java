/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.to.secad.aede.service;

import br.gov.to.secad.aede.domain.PerfilUsuarioOrgao;
import br.gov.to.secad.aede.repository.IPerfilUsuarioOrgaoRepository;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import static org.springframework.data.jpa.domain.Specifications.where;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author alex.santos
 */
@Service
public class PerfilUsuarioOrgaoService {

    @Autowired
    public IPerfilUsuarioOrgaoRepository iPerfilUsuarioOrgaoRepository;

    public List<PerfilUsuarioOrgao> findByPerfilUsuarioId(Integer permissaoIdUsuario) {
        try {
            return iPerfilUsuarioOrgaoRepository.findAll(where(especificatioPerfilUsuarioOrgao(permissaoIdUsuario, "I", "permissaoUsuario", "id", "")).
                    and(especificatioPerfilUsuarioOrgao("N", "S", "excluido", "", "")));
        } catch (Exception e) {
            System.out.println("erro ao buscar PerfilUsuarioRepositorioOrgao : " + e.getLocalizedMessage());
            return null;
        }
    }

    public List<PerfilUsuarioOrgao> findByPerfilIdUsuarioCpf(Integer permissaoIdUsuario, String usuarioCpf) {
        try {
            return iPerfilUsuarioOrgaoRepository.findAll(where(especificatioPerfilUsuarioOrgao(permissaoIdUsuario, "I", "permissaoUsuario", "id", "")).
                    and(especificatioPerfilUsuarioOrgao(usuarioCpf, "S", "permissaoUsuario", "usuario", "cpf")).
                    and(especificatioPerfilUsuarioOrgao("N", "S", "excluido", "", "")).
                    and(especificatioPerfilUsuarioOrgao("S", "S", "ativo", "", "")));
        } catch (Exception e) {
            System.out.println("erro ao buscar PerfilUsuarioRepositorioOrgao : " + e.getLocalizedMessage());
            return null;
        }
    }

    public List<PerfilUsuarioOrgao> findByPerfilIdUsuarioCpfOrgao(Integer permissaoUsuarioId, String usuarioCpf, Integer orgaoId, Integer perfilId) {
        System.out.println(" aelx aqui."+perfilId);
        try {
            return (List<PerfilUsuarioOrgao>) iPerfilUsuarioOrgaoRepository.findAll(where(
                    especificatioPerfilUsuarioOrgao(perfilId, "I", "permissaoUsuario", "perfil", "id")).
                    and(especificatioPerfilUsuarioOrgao(permissaoUsuarioId, "I", "permissaoUsuario", "id", "")).
                    and(especificatioPerfilUsuarioOrgao(usuarioCpf, "S", "permissaoUsuario", "usuario", "cpf")).
                    and(especificatioPerfilUsuarioOrgao("N", "S", "excluido", "", "")).
                    and(especificatioPerfilUsuarioOrgao("S", "S", "ativo", "", "")).
                    and(especificatioPerfilUsuarioOrgao(orgaoId, "S", "orgao", "id", ""))
            );
        } catch (Exception e) {
            System.out.println("erro ao buscar PerfilUsuarioRepositorioOrgao : " + e.getLocalizedMessage());
            return null;
        }
    }

    public PerfilUsuarioOrgao findByPerfilUsuarioOrgaoId(Integer perfilUsuarioOrgaoId) {
        try {
            return (PerfilUsuarioOrgao) iPerfilUsuarioOrgaoRepository.findOne(where(especificatioPerfilUsuarioOrgao(perfilUsuarioOrgaoId, "I", "id", "", null)).
                    and(especificatioPerfilUsuarioOrgao("N", "S", "excluido", "", "")));
        } catch (Exception e) {
            System.out.println("erro ao buscar PerfilUsuarioRepositorioOrgao : " + e.getLocalizedMessage());
            return null;
        }
    }

    public Specification<PerfilUsuarioOrgao> especificatioPerfilUsuarioOrgao(final Object object, final String tipo, final String fieldCampo, final String filhoField, final String filhoFilhoField) {

        return new Specification<PerfilUsuarioOrgao>() {
            @Override
            public Predicate toPredicate(Root<PerfilUsuarioOrgao> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                if (object != null) {
                    query.distinct(true);
                    if (tipo.equals("I")) {
                        if (!object.equals(0)) {
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

    @Transactional
    public boolean salvar(PerfilUsuarioOrgao perfilUsuarioOrgao) {
        try {
            iPerfilUsuarioOrgaoRepository.save(perfilUsuarioOrgao);
        } catch (Exception ex) {
            System.out.println("ERRO: " + ex.getMessage());
            return false;
        }
        return true;
    }

    @Transactional
    public boolean remover(PerfilUsuarioOrgao perfilUsuarioOrgao) {
        try {
            perfilUsuarioOrgao.setExcluido("S");
            iPerfilUsuarioOrgaoRepository.save(perfilUsuarioOrgao);
        } catch (Exception ex) {
            System.out.println("ERRO: " + ex.getLocalizedMessage());
            return false;
        }
        return true;
    }

}
