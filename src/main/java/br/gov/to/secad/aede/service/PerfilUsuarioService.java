/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.to.secad.aede.service;

 import br.gov.to.secad.aede.domain.Perfil;
import br.gov.to.secad.aede.domain.PerfilUsuarioOrgao;
import br.gov.to.secad.aede.domain.PerfilUsuario;
import br.gov.to.secad.aede.domain.Usuario;
import br.gov.to.secad.aede.util.FiltroEspecification;
import br.gov.to.secad.aede.view.LoginController;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import static org.springframework.data.jpa.domain.Specifications.where;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.gov.to.secad.aede.repository.IPerfilUsuarioRepository;

/**
 * Classe que fornece os serviços para o spring manipular a classe Fator
 *
 * @author wellyngton.santos
 */
@Service
public class PerfilUsuarioService {

    /**
     * Atributo iFatorRepository usado para tratar ações na entidade Fator no
     * banco.
     */
    @Autowired
    IPerfilUsuarioRepository iPermissaoRepository;

    /**
     * Método usado para acionar o método de busca no repositório de Fator. O
     * argumento recebe o valor de um nome para ser pesquisado, o valor é setado
     * na chamada de método feita pela instância iFatorRepository.
     */
    @Transactional(timeout = 10)
    public List<PerfilUsuario> findByUsuario(Usuario usuario) {
        try {
            //System.out.println("passou service");
            return iPermissaoRepository.findByUsuario(usuario);
        } catch (Exception e) {
            System.out.println("ERRO: " + e.getLocalizedMessage());
            return null;
        }
    }

    @Transactional(timeout = 10)
    public PerfilUsuario findByCpfPerfil(String cpf, Integer idPerfil) {
        try {
            //System.out.println("passou service");
            return iPermissaoRepository.findByCpfPerfil(cpf, idPerfil);
        } catch (Exception e) {
            System.out.println("ERRO: " + e.getLocalizedMessage());
            return null;
        }
    }

    @Transactional(timeout = 10)
    public PerfilUsuario findByCpfPerfilOrgao(String cpf, Integer idPerfil, Integer idOrgao) {
        try {
            return iPermissaoRepository.findByCpfPerfilOrgao(cpf, idPerfil,idOrgao);
        } catch (Exception e) {
            System.out.println("ERRO: " + e.getLocalizedMessage());
            return null;
        }
    }

    @Transactional(timeout = 10)
    public PerfilUsuario findByCpfUuario(Usuario cpf) {
        try {
            return iPermissaoRepository.findByCpfUuario(cpf);
        } catch (Exception e) {
            System.out.println("ERRO: " + e.getLocalizedMessage());
            return null;
        }
    }

    public PerfilUsuario findOnePermissao(Integer id) {
        return iPermissaoRepository.findOne(id);
    }

    public Page<PerfilUsuario> findAllPermissaoUsuario(PageRequest request, Object cpf) {
        if (request.getSort() != null) {
            request.getSort().and(new Sort(new Sort.Order(Sort.Direction.DESC, "id")));
        } else {
            PageRequest tmp = new PageRequest(request.getPageNumber(), request.getPageSize(), new Sort(new Sort.Order(Sort.Direction.DESC, "id")));
            request = tmp;
        }
        // busca a sessao 
        //LoginController logincontroller = (LoginController) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loginController");
//        System.out.println(" valor id orgao " + regional_id);
        //os dois jeitos funciona        
        return iPermissaoRepository.findAll(where(especificationPermissaoFiltreG(cpf, "S", "cpf", null)).
                and(especificationPermissaoFiltreG(LoginController.getOrgaoId(), "I", "lotadoOrgaoId", null)).
                and(especificationPermissaoFiltreG(LoginController.getRegionalId(), "S", "lotadoRegionalId", null)).
                and(especificationPermissaoFiltreG(LoginController.getSetorId(), "S", "lotadoSetorId", null)), request);
    }

    public Specification<PerfilUsuario> especificationPermissaoFiltreG(final Object object, final String tipo,
            final String fieldCampo, final String fieldFilho1) {

        return new Specification<PerfilUsuario>() {
            @Override
            public Predicate toPredicate(Root<PerfilUsuario> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                if (object != null) {
                    switch (tipo) {
                        case "I":
                            return cb.like(cb.lower(root.<String>get(fieldCampo)), Integer.getInteger(object.toString()) > 0 && Integer.getInteger(object.toString()) != null ? "%" + object + "%" : null);
                        case "S":
                            //Consulta quando for pelo nome do convenio
                            return cb.like(cb.lower(root.<String>get(fieldCampo)), object.toString() != null ? getLikePattern(object.toString()) : null);
                        case "O":
                            return cb.like(cb.lower(root.get(fieldCampo).<String>get(fieldFilho1)), getLikePattern(object.toString()));
                        default:
                            return null;
                    }
                }
                return null;
            }

            private String getLikePattern(final String searchTerm) {
                StringBuilder pattern = new StringBuilder();
                pattern.append("%");
                pattern.append(searchTerm.toLowerCase());
                pattern.append("%");
                return pattern.toString();
            }
        };
    }

    public List<PerfilUsuario> listarPermissaoPerfilOrgao(Integer perfilId , Integer orgaoId) {
        try {
            return iPermissaoRepository.findAll(where(especificationPermissaoPerfilorgao()).
                    and(especificationPermissaoPerfil()).
                    and(especificantionFiltros(new FiltroEspecification(perfilId, "I", "perfil", "id", "", "equal", "", true))).
                    and(especificantionFiltros(new FiltroEspecification(orgaoId, "I", "perfilUsuarioOrgaos", "orgao", "id", "equal", "", true))));
        } catch (Exception e) {
            System.out.println("erro->avaliacao->listarPermissaoPerfilOrgao: Service " + e.getMessage());
            return null;
        }
    }

    public Specification<PerfilUsuario> especificationPermissaoPerfilorgao() {

        return new Specification<PerfilUsuario>() {
            @Override
            public Predicate toPredicate(Root<PerfilUsuario> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                Root<PerfilUsuarioOrgao> perfilOrgao = query.from(PerfilUsuarioOrgao.class);
                
                return cb.equal(root.get("perfilUsuarioOrgaos"), perfilOrgao);
            }
        };
    }

    public Specification<PerfilUsuario> especificationPermissaoPerfil() {

        return new Specification<PerfilUsuario>() {
            @Override
            public Predicate toPredicate(Root<PerfilUsuario> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Root<Perfil> perfil = query.from(Perfil.class);
                return cb.equal(root.get("perfil"), perfil);
            }
        };

    }

    @Transactional
    public boolean salvar(PerfilUsuario permissaoUsuario) {
        try {
            iPermissaoRepository.save(permissaoUsuario);
        } catch (Exception ex) {
            System.out.println("ERRO: PermissaoService->salvar " + ex.getLocalizedMessage());
            return false;
        }
        return true;
    }

    public List<PerfilUsuario> listarPermissaoUsuario(String cpf) {
        try {

            return iPermissaoRepository.findAll(where(especificantionFiltros(new FiltroEspecification(cpf, "I", "usuario", "cpf", "", "equal", "", true))));
        } catch (Exception e) {
            System.out.println("erro->avaliacao->listaAvaliacaoMatricula: Service " + e.getMessage());
            return null;
        }
    }

    public Specification<PerfilUsuario> especificantionFiltros(final FiltroEspecification filtro) {
        return new Specification<PerfilUsuario>() {
            @Override
            public Predicate toPredicate(Root<PerfilUsuario> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                //query.distinct(filtro.isDistinct());
                try {
                    if ((!filtro.getValor().equals(0) && !filtro.getValor().equals("")) || filtro.getTipoComparacao().equals("isNull")) {
                        switch (filtro.getTipoComparacao()) {
                            case "equal":
                                if (!filtro.getFilderNivel2().equals("")) {
                                    if (!filtro.getFilderNivel3().equals("")) {
                                        return cb.equal(root.get(filtro.getFildeNivel1()).get(filtro.getFilderNivel2()).get(filtro.getFilderNivel3()), filtro.getValor());
                                    } else {
                                        return cb.equal(root.get(filtro.getFildeNivel1()).get(filtro.getFilderNivel2()), filtro.getValor());
                                    }
                                } else {
                                    return cb.equal(root.get(filtro.getFildeNivel1()), filtro.getValor());
                                }
                            case "or":
                                if (!filtro.getFilderNivel2().equals("")) {
                                    if (!filtro.getFilderNivel3().equals("")) {
                                        return cb.notEqual(root.get(filtro.getFildeNivel1()).get(filtro.getFilderNivel2()).get(filtro.getFilderNivel3()), filtro.getValor());
                                    } else {
                                        return cb.notEqual(root.get(filtro.getFildeNivel1()).get(filtro.getFilderNivel2()), filtro.getValor());
                                    }
                                } else {
                                    return cb.notEqual(root.get(filtro.getFildeNivel1()), filtro.getValor());
                                }
                            case "isNull":
                                if (!filtro.getFilderNivel2().equals("")) {
                                    if (!filtro.getFilderNivel3().equals("")) {
                                        return cb.isNull(root.get(filtro.getFildeNivel1()).get(filtro.getFilderNivel2()).get(filtro.getFilderNivel3()));
                                    } else {
                                        return cb.isNull(root.get(filtro.getFildeNivel1()).get(filtro.getFilderNivel2()));
                                    }
                                } else {
                                    return cb.isNull(root.get(filtro.getFildeNivel1()));
                                }
                            case "notEqual":
                                if (!filtro.getFilderNivel2().equals("")) {
                                    if (!filtro.getFilderNivel3().equals("")) {
                                        return cb.notEqual(root.get(filtro.getFildeNivel1()).get(filtro.getFilderNivel2()).get(filtro.getFilderNivel3()), filtro.getValor());
                                    } else {
                                        return cb.notEqual(root.get(filtro.getFildeNivel1()).get(filtro.getFilderNivel2()), filtro.getValor());
                                    }
                                } else {
                                    return cb.notEqual(root.get(filtro.getFildeNivel1()), filtro.getValor());
                                }
                            case "like":
                                return cb.like(cb.lower(root.<String>get(filtro.getFildeNivel1())), filtro.getValor().toString() != null ? getLikePattern(filtro.getValor().toString()) : null);
                        }
                    }
                } catch (Exception ex) {
                    System.out.println("Erro ao montar Specification ->AvaliacaoService" + ex.getMessage());
                }
                return null;
            }

            private String getLikePattern(final String searchTerm) {
                StringBuilder pattern = new StringBuilder();
                pattern.append("%");
                pattern.append(searchTerm.toLowerCase());
                pattern.append("%");
                return pattern.toString();
            }
        };
    }

}
