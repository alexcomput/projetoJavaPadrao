package br.gov.to.secad.aede.view;

import br.gov.to.secad.aede.util.MensagensController;
import br.gov.to.secad.ergon.domain.Servidor;
import br.gov.to.secad.aede.domain.Log;
import br.gov.to.secad.aede.domain.Menu;
import br.gov.to.secad.aede.domain.Orgao;
import br.gov.to.secad.aede.domain.PerfilUsuarioOrgao;
import br.gov.to.secad.aede.domain.PerfilUsuario;
import br.gov.to.secad.aede.domain.Usuario;
import br.gov.to.secad.aede.service.AuthenticationService;
import br.gov.to.secad.ergon.service.ServidorService;
import br.gov.to.secad.aede.service.LogService;
import br.gov.to.secad.aede.service.MenuService;
import br.gov.to.secad.aede.service.PerfilUsuarioOrgaoService;
import br.gov.to.secad.aede.service.PerfilUsuarioService;
import br.gov.to.secad.aede.service.UsuarioService;
import br.gov.to.secad.aede.util.HashPassword;
import br.gov.to.secad.aede.util.CpfUtil;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.model.menu.MenuModel;
import org.springframework.web.jsf.FacesContextUtils;

/**
 * Classe controladora da página de login
 *
 * @author wellyngton.santos
 */
/**
 * Definição de objeto no springframework, definido como um controller.
 * Definindo o scopo da classe que será de sessão. Declaração de ManagedBean do
 * JSF
 *
 * @Named
 */
@ManagedBean(name = "loginController")
@SessionScoped
public class LoginController implements Serializable {

    private static final long serialVersionUID = 1L;

    @ManagedProperty(value = "#{authenticationService}")
    private AuthenticationService authenticationService;

    @ManagedProperty(value = "#{logService}")
    private LogService logService;

    @ManagedProperty(value = "#{servidorService}")
    private ServidorService servidorService;

    @ManagedProperty(value = "#{perfilUsuarioOrgaoService}")
    private PerfilUsuarioOrgaoService perfilUsuarioOrgaoService;

    @ManagedProperty(value = "#{usuarioService}")
    private UsuarioService usuarioService;

    private ArrayList<Menu> listaMenu;

    private List<PerfilUsuario> listaPermissao;

    /**
     * Atributo que armazena a instância que foi criada na página para a ação de
     * login
     */
    private Servidor servidor;
    /**
     * Atributo que armazena o cpf que servirá como username ou login do usuário
     */
    public String userCpf;

    private Usuario usuario;

    private String regionalId;
    private String regionalDescricao;

    private Integer orgaoId;
    private static String orgaoDescricao;
    /*
     Orgao no qual o usuario está utilizando para acessar o sistema
     */
    private Orgao orgaoPerfilAcesso;

    private Orgao orgao;
    /*
     Lista de todos os Orgaos de acesso do usuario
     */
    private List<PerfilUsuarioOrgao> listaPerfilUsuarioOrgaos;

    private Integer perfilUsuarioOrgaoId;

    private String setorId;
    private String setorDescricao;

    /**
     * Atributo que armazena a string digita pelo usuário para senha.
     */
    public String password;
    /**
     * Atributo que guarda a informação se o usuário está ou não logado
     */
    public boolean autenticado;

    public Integer idPermissaoAtual;
    /*
     Essa permissao é a qual o usuário acessa o sistema
     */
    private PerfilUsuario permissaoAtual = new PerfilUsuario();

    /*
     Essa lista de permissao é  onde fica armazenado todos as permissao do usuário.
     Caso o mesmo contém mais que 1 permissao
     */
    public List<PerfilUsuario> permissoes;

    @ManagedProperty(value = "#{permissaoService}")
    private PerfilUsuarioService permissaoService;

    @ManagedProperty(value = "#{menuService}")
    private MenuService menuService;

    private MenuModel modelMenu;

    private List< Menu> menusAcesso;

    @PostConstruct
    public void init() {
        servidor = new Servidor();
        autenticado = false;
    }

    public void setMenusAcesso(List<Menu> menus) {
        menusAcesso = menus;
    }

    public List<Menu> getMenusAcesso() {
        return menusAcesso;
    }

    public UsuarioService getUsuarioService() {
        return usuarioService;
    }

    public void setUsuarioService(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    public Servidor getServidor() {
        return servidor;
    }

    public ArrayList<Menu> getListaMenu() {
        return listaMenu;
    }

    public void setListaMenu(ArrayList<Menu> listaMenu) {
        this.listaMenu = listaMenu;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Integer getPerfilUsuarioOrgaoId() {
        return perfilUsuarioOrgaoId;
    }

    public void setPerfilUsuarioOrgaoId(Integer perfilUsuarioOrgaoId) {
        this.perfilUsuarioOrgaoId = perfilUsuarioOrgaoId;
    }

    /**
     * Método seta no atributo formulário o retorno do usuário autenticado.
     */
    public void setServidor(String userCpf) {
        try {
            // System.out.println(userCpf);
            this.servidor = servidorService.findByCpfInterno(userCpf);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            if (userCpf.contains("\"")) {
                System.out.println("ASPAS");
            }
            this.servidor = new Servidor();
            this.servidor.setNome("Teste");
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) throws NoSuchAlgorithmException {
        this.password = HashPassword.md5(password);
    }

    public List<PerfilUsuario> getListaPermissao() {
        return listaPermissao;
    }

    public void setListaPermissao(List<PerfilUsuario> listaPermissao) {
        this.listaPermissao = listaPermissao;
    }

    public String getUserCpf() {
        if (userCpf == null) {
            return "";
        } else {
            return userCpf;
        }
    }

    public String getCpfFormatado() {
        if (userCpf == null) {
            return "";
        } else {
            return CpfUtil.imprimeCPF(CpfUtil.cpfConverteString(userCpf));
        }
    }

    public void setUserCpf(String userCpf) {
        this.userCpf = userCpf.replaceAll("\\.", "").replace("-", "");
    }

    public Integer getIdPermissaoAtual() {
        return idPermissaoAtual;
    }

    public void setIdPermissaoAtual(Integer idPermissaoAtual) {
        this.idPermissaoAtual = idPermissaoAtual;
    }

    public PerfilUsuario getPermissaoAtual() {
        return permissaoAtual;
    }

    public void setPermissaoAtual(PerfilUsuario permissaoAtual) {
        this.permissaoAtual = permissaoAtual;
    }

    public List<PerfilUsuario> getPermissoes() {
        return permissoes;
    }

    public void setPermissoes(List<PerfilUsuario> permissoes) {
        this.permissoes = permissoes;
    }

    public PerfilUsuarioOrgaoService getPerfilUsuarioOrgaoService() {
        return perfilUsuarioOrgaoService;
    }

    public void setPerfilUsuarioOrgaoService(PerfilUsuarioOrgaoService perfilUsuarioOrgaoService) {
        this.perfilUsuarioOrgaoService = perfilUsuarioOrgaoService;
    }

    public Orgao getOrgaoPerfilAcesso() {
        return orgaoPerfilAcesso;
    }

    public void setOrgaoPerfilAcesso(Orgao orgaoPerfilAcesso) {
        this.orgaoPerfilAcesso = orgaoPerfilAcesso;
    }

    public List<PerfilUsuarioOrgao> getListaPerfilUsuarioOrgaos() {
        return listaPerfilUsuarioOrgaos;
    }

    public void setListaPerfilUsuarioOrgaos(List<PerfilUsuarioOrgao> listaPerfilUsuarioOrgaos) {
        this.listaPerfilUsuarioOrgaos = listaPerfilUsuarioOrgaos;
    }

    /**
     * Método usado para autenticar o usuário no sistema. Aqui o método utiliza
     * o valor dos atributos userCpf e password setados pelo usuário na página
     * para tentar realizar a autenticação, que será confirmada pela chamada do
     * método login da instância de authenticationService.
     *
     * @return
     */
    public boolean cpfValido() {
        if (CpfUtil.isCPF(userCpf)) {
            return true;
        } else {
            MensagensController.addError("O Cpf digitado não é um CPF Válido!");
            return false;
        }
    }

    public String logar() {
        String retorno = "";
        if (cpfValido()) {
            usuario = new Usuario();
            usuario.setCpf(userCpf);
            usuario.setSenhahash(password);
            if (usuarioService.findByCpfSenha(usuario.getCpf(), usuario.getSenhahash())) {
                listaPermissao = permissaoService.findByUsuario(usuario);
                usuario.setPermissoes(listaPermissao);
                if (!listaPermissao.isEmpty()) {

                    if (listaPermissao != null && listaPermissao.size() >= 2) {
                        return "escolhaPerfil";
                    } else {
                        idPermissaoAtual = listaPermissao.get(0).getId();
                        buscarPerfilUsuarioOrgaoPermissaoId();

                        if (listaPerfilUsuarioOrgaos.size() >= 2) {
                            return "escolhaPerfil";
                        } else {
                            retorno = autenticacaoUsuario(listaPermissao.get(0), null);
                        }
                    }
                } else {
                    MensagensController.addError("O usuário não contém nenhum Orgao atribuido!");
                }
            } else {
                MensagensController.addWarn("Usuário ou Senha Inválidos!");
                return "login";

            }
        }
        return retorno;
    }

    public String autenticacaoUsuario(PerfilUsuario permissaoAute, PerfilUsuarioOrgao perfilUsuarioOrgao) {
        permissaoAtual = listaPermissao.get(listaPermissao.indexOf(permissaoAute));
        /* 
        Atribui ao usuário com qual perfil o mesmo irá acesasr o sistema        
         */
        
          // Confere se o mesmo contém permissão para algum orgão 
        if (!permissaoAtual.getPerfilUsuarioOrgaos().isEmpty()) {
            if (authenticationService.login(permissaoAute.getUsuario())) {

                if (listaPermissao != null && !listaPermissao.isEmpty()) {
                    setPermissoes(listaPermissao);
                    // Verefica se as permissoes do usuário é mais que 1..então. 
                    idPermissaoAtual = permissaoAtual.getId();

                    setMenusAcesso(menuService.findByMenu(usuario.getCpf(), permissaoAtual.getPerfil().getId()));
                    // BUSCA OS PERFILSUUSARIOORGAOPERMISSAO que o usuário tem acesso.. todos os orgãos.
                    buscarPerfilUsuarioOrgaoPermissaoId();
                    // Quando for na hora de logar o sistema pegará sempre a primeira possição do vetor.
                    if (perfilUsuarioOrgao == null) {
                        orgaoPerfilAcesso = permissaoAtual.getPerfilUsuarioOrgaos().get(0).getOrgao();
                    } else {
                        //orgaoPerfilAcesso = permissaoAtual.getPerfilUsuarioOrgaos().get(permissaoAtual.getPerfilUsuarioOrgaos().indexOf(perfilUsuarioOrgao)).getOrgao();
                        orgaoPerfilAcesso = perfilUsuarioOrgao.getOrgao();
                    }

                    // Atribui os valores na sessao 
                    orgaoDescricao = permissaoAtual.getLotadoOrgao();
                    regionalId = permissaoAtual.getLotadoRegionalId();
                    regionalDescricao = permissaoAtual.getRegional();
                    setorDescricao = permissaoAtual.getLotadoSetor();
                    setorId = permissaoAtual.getLotadoSetorId();
                    setServidor(authenticationService.getUsuarioLogado().getCpf());
                    autenticado = true;
                    return "/index?faces-redirect=true";

                } else {
                    MensagensController.addWarn("Nao contem permissao para o Usuário!");
                    return "login";
                }
            } else {
                MensagensController.addWarn("Usuário ou Senha Inválidos!");
                return "login";

            }
        } else {
            logout();
            MensagensController.addError("O usuário não contém nenhum Orgao atribuido!");
            return "login";
        }
    }

    public void buscarPerfilUsuarioOrgaoPermissaoId() {
        //Busca todos os perfilUsuarioOrgao.. os orgao que aquele perfil tem acesso.
        permissaoAtual.setPerfilUsuarioOrgaos(perfilUsuarioOrgaoService.findByPerfilUsuarioId(idPermissaoAtual));
        listaPerfilUsuarioOrgaos = permissaoAtual.getPerfilUsuarioOrgaos();
    }

    public static Orgao getOrgao() {
        return getCurrentInstance().orgao;
    }

    public void setOrgao(Orgao orgao) {
        this.orgao = orgao;
    }

    public String trocaPerfilOrgao(PerfilUsuarioOrgao perfilUsuarioOrgao) {
        System.out.println("Aqui troca 1");
        Usuario usuarioLocal = usuario;
        authenticationService.logout();
        usuario = usuarioLocal;
        System.out.println("Aqui troca 2");
        return autenticacaoUsuario(perfilUsuarioOrgao.getPermissaoUsuario(), perfilUsuarioOrgao);
    }

    /**
     * Método usado ao escolher um perfil de usuário, para usuário que possuem
     * permissões de perfis diferentes.
     *
     * @return
     */
    public String escolhePerfil() {
        //boolean sucesso = authenticationService.login(userCpf, password);
        //boolean sucesso = authenticationService.login(usuario);
        //selecionar perfil atual        

        for (PerfilUsuario pe : listaPermissao) {
            if (pe.getId().equals(idPermissaoAtual)) {
                return autenticacaoUsuario(pe, perfilUsuarioOrgaoService.findByPerfilUsuarioOrgaoId(perfilUsuarioOrgaoId));
            }
        }
        return "";
    }

    public MenuModel getModelMenu() {
        return modelMenu;
    }

    public void setModelMenu(MenuModel modelMenu) {
        this.modelMenu = modelMenu;
    }

    /**
     * Método para efetuar o logout do usuário no sistema. Utiliza uma chamada
     * ao método logout do serviço de autenticação.
     *
     * @return
     */
    public String logout() {
        autenticado = false;
        servidor = new Servidor();
        authenticationService.logout();
        return "sair";
    }

    /**
     * Método acionado quando o usuário limpa o formulário de login na página
     */
    public void limpar() {
        userCpf = new String();
        password = new String();
    }

    /**
     * Método para verificar se o usuário está logado ou não.
     */
    public boolean isAutenticado() {
        return autenticado;
    }

    public void setAutenticado(boolean autenticado) {
        this.autenticado = autenticado;
    }

    /**
     * Método utilizado para rastrear em qual máquina foi realizada a ação no
     * sistema. Aqui o método pega do contexto qual o valor do ip que realizou a
     * ação.
     */
    public static String getIp() {
        try {
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            String ipAddress = request.getHeader("X-FORWARDED-FOR");
            if (ipAddress == null) {
                ipAddress = request.getRemoteAddr();
            }
            return ipAddress;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Método que salva o Log da aplicação
     */
    public void log(String acao) {
        try {
            logService.save(new Log(getUserCpf(), getIp(), acao));
        } catch (Exception e) {
            System.out.println("ERRO AO GRAVAR LOG");
        }
    }

    /**
     * Método para verificar a instância atual do objeto LoginController
     *
     * @return
     */
    public static LoginController getCurrentInstance() {
        return (LoginController) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loginController");

        //return FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance()).getBean(LoginController.class);
    }

    /**
     * Método para verificar órgão do usuário logado atualmente
     */
    public static Integer getOrgaoId() {
        try {
            // System.out.println("getOrgaoId - " + getCurrentInstance().getPermissaoAtual().getOrgao_id());
            //getCurrentInstance().getPermissaoAtual().getOrgao_id();
            return getCurrentInstance().orgaoId;
        } catch (Exception e) {
            System.err.println("erro getCodOrgao - " + e.getMessage());
            return null;
        }
    }

    public void setOrgaoId(Integer orgaoId) {
        this.orgaoId = orgaoId;
    }

    public void setOrgaoDescricao(String orgaoDescricao) {
        this.orgaoDescricao = orgaoDescricao;
    }

    public static String getOrgaoDescriString() {
        return getCurrentInstance().orgaoDescricao;
    }

    public static String getRegionalId() {
        try {
            //return getCurrentInstance().getPermissaoAtual().getRegional_id();
            return getCurrentInstance().regionalId;
        } catch (Exception e) {
            System.err.println("erro getCodRegional - " + e.getMessage());
            return null;
        }
    }

    public void setRegionalId(String regionalId) {
        this.regionalId = regionalId;
    }

    /**
     * Método para verificar regional do usuário logado atualmente
     */
    public static String getRegionalDescricao() {
        try {
            //return getCurrentInstance().getPermissaoAtual().getRegional_id();
            return getCurrentInstance().regionalDescricao;
        } catch (Exception e) {
            System.err.println("erro getCodRegional - " + e.getMessage());
            return null;
        }
    }

    public void setRegionalDescricao(String regionalDescricao) {
        this.regionalDescricao = regionalDescricao;
    }

    /**
     * Método para verificar setor do usuário logado atualmente
     */
    public static String getSetorId() {
        try {
            //return getCurrentInstance().getPermissaoAtual().getSetor_id();
            return getCurrentInstance().setorId;
        } catch (Exception e) {
            System.err.println("erro getCodSetor - " + e.getMessage());
            return null;
        }
    }

    public void setSetorId(String setorId) {
        this.setorId = setorId;
    }

    public static String getSetorDescricao() {
        return getCurrentInstance().setorDescricao;
    }

    public void setsetorDescricao(String setorDescricao) {
        this.setorDescricao = setorDescricao;
    }

    public static Integer getAutorizacao() {
        //System.out.println("Autorização aqui =" + getCurrentInstance().getPermissaoAtual().getPerfil().getId());
        return getCurrentInstance().permissaoAtual.getPerfil().getId();
        //return getCurrentInstance().getPermissaoAtual().getPerfil().getId();
    }

    /**
     * Método para verificar órgão do usuário logado atualmente
     */
    public static String getCpf() {
        try {
            return getCurrentInstance().usuario.getCpf();
        } catch (Exception e) {
            return null;
        }
    }

    public static void setLog(String acao) {
        try {
            getCurrentInstance().log(acao);
        } catch (Exception e) {
            System.err.println("ERRO AO GRAVAR O LOG: " + e.getMessage());
        }
    }

    public AuthenticationService getAuthenticationService() {
        return authenticationService;
    }

    public void setAuthenticationService(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    public LogService getLogService() {
        return logService;
    }

    public void setLogService(LogService logService) {
        this.logService = logService;
    }

    public ServidorService getServidorService() {
        return servidorService;
    }

    public void setServidorService(ServidorService servidorService) {
        this.servidorService = servidorService;
    }

    public PerfilUsuarioService getPermissaoService() {
        return permissaoService;
    }

    public void setPermissaoService(PerfilUsuarioService permissaoService) {
        this.permissaoService = permissaoService;
    }

    public MenuService getMenuService() {
        return menuService;
    }

    public void setMenuService(MenuService menuService) {
        this.menuService = menuService;
    }

}
