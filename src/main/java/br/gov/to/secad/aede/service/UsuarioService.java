/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.to.secad.aede.service;

 import br.gov.to.secad.aede.domain.Usuario;
import br.gov.to.secad.aede.repository.IUsuarioRepository;
import br.gov.to.secad.aede.util.FiltroEspecification;
import br.gov.to.secad.aede.util.MensagensController;
import java.util.ArrayList;
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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Classe que fornece os serviços para o spring manipular a classe Usuario
 *
 * @author wellyngton.santos
 */
@Service
@Transactional(readOnly = true)
public class UsuarioService implements UserDetailsService {

    /**
     * Atributo iusuarioRepository usado para tratar ações na entidade Usuario
     * no banco.
     */
    @Autowired
    private IUsuarioRepository iUsuarioRepository;

    /**
     * Método usado para buscar usuários pelo cpf na Entidade Usuário
     */
    @Transactional(timeout = 10)
    public Usuario findByCpf(String cpf) {
        try {
            Usuario usu = iUsuarioRepository.findByCpf(cpf);
            if (usu == null) {
                System.out.println("vazio");
            }
            System.out.println(usu);
            return usu;
        } catch (Exception e) {
            System.out.println("ERRO: " + e.getLocalizedMessage());
            return null;
        }
    }

    @Transactional(timeout = 10)
    public Usuario findByUsuarioAtivo(String cpf) {
        try {
            return (Usuario) iUsuarioRepository.findOne(where(especificantionFiltros(new FiltroEspecification(cpf, "S", "cpf", "", "", "equal", "", true))).
                    and(especificantionFiltros(new FiltroEspecification(true, "S", "ativo", "", "", "equal", "", true))));

        } catch (Exception e) {
            System.out.println("ERRO: " + e.getLocalizedMessage());
            return null;
        }
    }

    @Transactional(timeout = 10)
    public boolean findByCpfSenha(String cpf, String senha) {
        try {
            Usuario usu = iUsuarioRepository.findByCpfSenha(cpf, senha);
            if (usu == null) {
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Método usado para buscar usuários pelo email na Entidade Usuário
     */
    public Usuario findByEmail(String email) {
        try {
            return iUsuarioRepository.findByEmail(email);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Método usado para salvar usuários, na realidade esse método é usado para
     * atualizar a informação de email do usuário que ficará em branco.
     */
    @Transactional
    public void save(Usuario usuario) {
        try {
            iUsuarioRepository.save(usuario);
        } catch (Exception e) {
            System.out.println("ERRO: " + e.getLocalizedMessage());
        }
    }

    /**
     * Método da classe UserDetailsService usado para buscar usuários e realizar
     * autenticação.
     */
    @Override
    public UserDetails loadUserByUsername(String string) throws UsernameNotFoundException {
        return findByCpf(string);
    }

    @Transactional(timeout = 10)
    public ArrayList<Usuario> findByCpfLike(String cpf) {
        try {
            return iUsuarioRepository.findByCpfLike(cpf);
        } catch (Exception e) {
            MensagensController.addError("Erro ao buscar registro no banco! " + e.getLocalizedMessage());
        }
        return null;
    }

    public Usuario findOneUsuario(int parseInt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Page<Usuario> findAllUsuario(PageRequest request, Object cpf, Object email) {
        //sempre mostrar o último inserido se for null 
        if (request.getSort() != null) {
            request.getSort().and(new Sort(new Sort.Order(Sort.Direction.DESC, "id")));
        } else {
            // Mostra o que foi selecionado na GRID se é decrescente ou crescente de acordo o que foi selecionado na grid.
            //instancia a pagina de requisição passando o numero da pagina o tamanho e a ordenação
            PageRequest tmp = new PageRequest(request.getPageNumber(), request.getPageSize(), new Sort(new Sort.Order(Sort.Direction.DESC, "id")));
            request = tmp;
        }

        return iUsuarioRepository.findAll(where(especificationUsuarioFiltreG(cpf, "S", "cpf", null)).
                and(especificationUsuarioFiltreG(email, "S", "email", null)), request);
    }

    public Specification<Usuario> especificationUsuarioFiltreG(final Object object, final String tipo,
            final String fieldCampo, final String fieldFilho1) {

        return new Specification<Usuario>() {
            @Override
            public Predicate toPredicate(Root<Usuario> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
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

    public Specification<Usuario> especificantionFiltros(final FiltroEspecification filtro) {
        return new Specification<Usuario>() {
            @Override
            public Predicate toPredicate(Root<Usuario> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
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
