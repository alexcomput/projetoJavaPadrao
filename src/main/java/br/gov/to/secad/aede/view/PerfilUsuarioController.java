package br.gov.to.secad.aede.view;

import br.gov.to.secad.aede.domain.Orgao;
import br.gov.to.secad.aede.domain.Perfil;
import br.gov.to.secad.aede.domain.PerfilUsuarioOrgao;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import br.gov.to.secad.aede.service.PerfilUsuarioService;
import br.gov.to.secad.aede.domain.PerfilUsuario;
import br.gov.to.secad.ergon.domain.Servidor;
import br.gov.to.secad.aede.domain.Usuario;
import br.gov.to.secad.aede.service.OrgaoService;
import br.gov.to.secad.aede.service.PerfilUsuarioOrgaoService;
import br.gov.to.secad.ergon.service.ServidorService;
import br.gov.to.secad.aede.service.UsuarioService;
import br.gov.to.secad.aede.util.JDialogoUtil;
import br.gov.to.secad.aede.util.MensagensController;
import br.gov.to.secad.aede.util.CpfUtil;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

/**
 * Classe que controla as ações relacionadas a classe Fator.
 *
 * @author wellyngton.santos
 */
/**
 * Definição de objeto no springframework, definido como um controller.
 * Definindo o scopo da classe que será de sessão. Declaração de ManagedBean do
 * JSF
 */
@ManagedBean
@ViewScoped
public class PerfilUsuarioController implements Serializable {

    @ManagedProperty(value = "#{permissaoService}")
    private PerfilUsuarioService permissaoService;

    @ManagedProperty(value = "#{orgaoService}")
    private OrgaoService orgaoService;

    private Usuario UsuarioSelecionado;

    private PerfilUsuario permissaoUsuario;

    private Perfil perfil;

    private Integer nunFunc;
    private Integer nunVinc;

    private String cpfServidor;

    private Servidor servidor;
    private List<Orgao> listaOrgao;

    private Orgao orgao;

    /**
     * Atributo que instancia o serviço de transações com a classe Servidor
     */
    @ManagedProperty(value = "#{servidorService}")
    private ServidorService servidorService;

    @ManagedProperty(value = "#{usuarioService}")
    private UsuarioService usuarioService;

    @ManagedProperty(value = "#{perfilUsuarioOrgaoService}")
    private PerfilUsuarioOrgaoService perfilUsuarioOrgaoService;

    private PerfilUsuarioOrgao perfilUsuarioOrgao;

    // Lista de Orgaos que o usuario contém privilégio.
    private List<PerfilUsuarioOrgao> listaPerfilUsuarioOrgao;

    // Lista de Orgaos que o usuario contém privilégio.
    private List<PerfilUsuario> listaPermissao;

    private Usuario usuarioBanco;
    /*
     list é uma instancia do DATAMODEL da dataTable
     */
    private transient LazyDataModel<PerfilUsuario> list;

    @PostConstruct
    public void init() {
        listaOrgao = orgaoService.findAll();
        permissaoUsuario = new PerfilUsuario();
        this.list = new LazyDataModel<PerfilUsuario>() {
            @Override
            public List<PerfilUsuario> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

                //Variavel de retorno
                List<PerfilUsuario> result;
                //Seta o tamanho da pagina
                this.setPageSize(pageSize);

                if (sortOrder == SortOrder.UNSORTED || StringUtils.isBlank(sortField)) {
                    PageRequest request = new PageRequest(first / pageSize, pageSize);
                    Page<PerfilUsuario> page;
                    page = permissaoService.findAllPermissaoUsuario(request,
                            filters.get("cpf"));
                    this.setRowCount((int) page.getTotalElements());
                    result = page.getContent();
                } else {
                    Sort sort = new Sort(sortOrder == SortOrder.ASCENDING ? Sort.Direction.ASC : Sort.Direction.DESC, sortField);

                    PageRequest request = new PageRequest(first / pageSize, pageSize, sort);
                    Page<PerfilUsuario> page = permissaoService.findAllPermissaoUsuario(request, filters.get("cpf"));
                    this.setRowCount((int) page.getTotalElements());
                    result = page.getContent();
                }
                // retorna a lista com os registro
                return result;
            }

            @Override
            public Object getRowKey(PerfilUsuario object) {
                return object.getId().toString();
            }

            @Override
            public PerfilUsuario getRowData(String rowKey) {
                return permissaoService.findOnePermissao(Integer.parseInt(rowKey));
            }
        };
    }

    public void vereficaListaPermissao() {

    }

    public String cpfConvertido(String cpf) {
        System.out.println("é cpf = " + CpfUtil.isCPF(cpf));
        if (CpfUtil.isCPF(CpfUtil.cpfConverteString(cpf))) {
            return CpfUtil.imprimeCPF(CpfUtil.cpfConverteString(cpf));
        }
        return "";

    }

    public void limparCampos2() {
        servidor = null;
        permissaoUsuario = new PerfilUsuario();
        this.cpfServidor = "";
        listaPerfilUsuarioOrgao = null;
        usuarioBanco = null;
    }

    public void carregarServidor() {
        limparCampos2();
        //permissaoUsuario = new PerfilUsuario();
        if (this.nunFunc > 0 && this.nunVinc > 0) {
            servidor = servidorService.findByNumfuncNunvinc(this.nunFunc, this.nunVinc);
            if (servidor != null) {
                usuarioBanco = usuarioService.findByCpf(CpfUtil.cpfConverteString(servidor.getCpf()));
                if (usuarioBanco != null) {
                    permissaoUsuario.setUsuario(usuarioBanco);
                    permissaoUsuario.setEmailServidor(usuarioBanco.getEmail());
                    //permissaoUsuario.setNomeServidor(servidor.getNome());
                    permissaoUsuario.setDadosServidor(servidor);
                    buscarListaPerfilUsuarioCpf();
                    cpfServidor = servidor.getCpf();
                    MensagensController.addInfo("Servidor Carregado!");
                } else {
                    MensagensController.addError("usuário não contém no portal do servidor!");
                }
            } else {
                MensagensController.addError("Número de matrícula errado ou servidor não cadastrado na base de dados do ERGON!");
            }
            //permissaoService.findByUsuario(servidor);

        } else {
            MensagensController.addError("Erro ao carregar registro!");
        }
    }

    /*
     Pega o evento do JDIALOG que foi selecionado e atribui para o concedente
     */
    public void usuarioJDialogSelecionado(SelectEvent event) {
        permissaoUsuario.setUsuario((Usuario) event.getObject());
        this.cpfServidor = permissaoUsuario.getUsuario().getCpf();
        this.servidor = servidorService.findByCpfInterno(permissaoUsuario.getUsuario().getCpf());
        // Atribui as informações de onde é o servidor se o mesmo é de uma regional do orgao ETC
        permissaoUsuario.setDadosServidor(servidor);
        buscarListaPerfilUsuarioCpf();
    }

    public void buscarListaPerfilUsuarioOrgaoCpf() {
        listaPerfilUsuarioOrgao = perfilUsuarioOrgaoService.findByPerfilIdUsuarioCpf(permissaoUsuario.getId(), permissaoUsuario.getUsuario().getCpf());

    }

    public void buscarListaPerfilUsuarioCpf() {
        listaPermissao = permissaoService.listarPermissaoUsuario(permissaoUsuario.getUsuario().getCpf());

    }

    public void addPrivilegioUsuario() {

        PerfilUsuario permissaoUsuarioL = permissaoService.findByCpfPerfil(permissaoUsuario.getUsuario().getCpf(), perfil.getId());

        // Se nao conter permissao entao salva a mesma.. 
        if (permissaoUsuarioL == null) {
            permissaoUsuarioL = new PerfilUsuario();
            permissaoUsuarioL.setDadosServidor(servidor);
            permissaoUsuarioL.setPerfil(perfil);
            permissaoUsuarioL.setUsuario(usuarioBanco);
        }
        permissaoUsuarioL.setLotadoOrgao(permissaoUsuario.getLotadoOrgao());
        permissaoUsuarioL.setLotadoOrgaoId(permissaoUsuario.getLotadoOrgaoId());
        permissaoUsuarioL.setEmailServidor(permissaoUsuario.getEmailServidor());

        permissaoUsuarioL.setUsuario(permissaoUsuarioL.getUsuario());
        //else {
        //permissaoUsuario = permissaoUsuarioL;
        //}
        //} 
        perfilUsuarioOrgao = new PerfilUsuarioOrgao();
        perfilUsuarioOrgao.setOrgao(this.orgao);
        perfilUsuarioOrgao.setPermissaoUsuario(permissaoUsuarioL);

        List<PerfilUsuarioOrgao> perfilOrgTemp = new ArrayList<PerfilUsuarioOrgao>();
        perfilOrgTemp.add(perfilUsuarioOrgao);
        permissaoUsuarioL.setPerfilUsuarioOrgaos(perfilOrgTemp);

        //f (perfilUsuarioOrgaoService.salvar(perfilUsuarioOrgao)) {
        //permissaoUsuarioL.getPerfilUsuarioOrgaosAtivos().add(perfilUsuarioOrgao);
        if (permissaoService.salvar(permissaoUsuarioL)) {
            MensagensController.addInfo("Registro inserido com sucesso!");
            //listaPermissao.add(perfilUsuarioOrgao);
            buscarListaPerfilUsuarioCpf();
        } else {
            MensagensController.addError("Erro ao registrar registrar Permissao ao Usuario Órgão!");
        }
    }

    public void limparCampos() {
        servidor = null;
        this.orgao = null;
        this.perfil = null;
        permissaoUsuario = new PerfilUsuario();
        this.cpfServidor = "";
        listaPerfilUsuarioOrgao = null;
        this.nunFunc = null;
        this.nunVinc = null;

    }

    public void removerPerfilUsuarioOrgao(PerfilUsuarioOrgao perfilUsuarioOrgao) {
        if (perfilUsuarioOrgaoService.remover(perfilUsuarioOrgao)) {
            //listaPerfilUsuarioOrgao.remove(perfilUsuarioOrgao);
            buscarListaPerfilUsuarioCpf();

            MensagensController.addInfo("Registro excluído com sucesso!");
        } else {
            MensagensController.addInfo("Erro ao excluído registro!");
        }
    }

    public void abrirJDialogUsuario() {
        JDialogoUtil.abrirDialogo("usuarioJdialogView", "600", "100%", true, false, null);
    }

    /*
     Fechar o dialog e enviar por envente o objeto selecionado.
     */
    public void selecionardoUsuario(Usuario usuario) {
        RequestContext.getCurrentInstance().closeDialog(usuario);
    }

    public void onEdit(RowEditEvent event) {

    }

    public void onCancel(RowEditEvent event) {
    }

    public void listarPermissaoPerfilOrgao() {
        permissaoService.listarPermissaoPerfilOrgao(4, 2);
    }

    /*
     GETS E SETS
     */
    public List<PerfilUsuarioOrgao> getListaPerfilUsuarioOrgao() {
        return listaPerfilUsuarioOrgao;
    }

    public void setListaPerfilUsuarioOrgao(List<PerfilUsuarioOrgao> listaPerfilUsuarioOrgao) {
        this.listaPerfilUsuarioOrgao = listaPerfilUsuarioOrgao;
    }

    public PerfilUsuarioOrgaoService getPerfilUsuarioOrgaoService() {
        return perfilUsuarioOrgaoService;
    }

    public void setPerfilUsuarioOrgaoService(PerfilUsuarioOrgaoService perfilUsuarioOrgaoService) {
        this.perfilUsuarioOrgaoService = perfilUsuarioOrgaoService;
    }

    public PerfilUsuarioOrgao getPerfilUsuarioOrgao() {
        return perfilUsuarioOrgao;
    }

    public void setPerfilUsuarioOrgao(PerfilUsuarioOrgao perfilUsuarioOrgao) {
        this.perfilUsuarioOrgao = perfilUsuarioOrgao;
    }

    public Integer getNunFunc() {
        return nunFunc;
    }

    public void setNunFunc(Integer nunFunc) {
        this.nunFunc = nunFunc;
    }

    public Integer getNunVinc() {
        return nunVinc;
    }

    public void setNunVinc(Integer nunVinc) {
        this.nunVinc = nunVinc;
    }

    public String getCpfServidor() {
        return cpfServidor;
    }

    public Orgao getOrgao() {
        return orgao;
    }

    public void setOrgao(Orgao orgao) {
        this.orgao = orgao;
    }

    public List<Orgao> getListaOrgao() {
        return listaOrgao;
    }

    public void setListaOrgao(List<Orgao> listaOrgao) {
        this.listaOrgao = listaOrgao;
    }

    public OrgaoService getOrgaoService() {
        return orgaoService;
    }

    public void setOrgaoService(OrgaoService orgaoService) {
        this.orgaoService = orgaoService;
    }

    public void setCpfServidor(String cpfServidor) {
        this.cpfServidor = cpfServidor;
    }

    public PerfilUsuario getPermissaoUsuario() {
        return permissaoUsuario;
    }

    public void setPermissaoUsuario(PerfilUsuario permissaoUsuario) {
        this.permissaoUsuario = permissaoUsuario;
    }

    public PerfilUsuarioService getPermissaoService() {
        return permissaoService;
    }

    public void setPermissaoService(PerfilUsuarioService permissaoService) {
        this.permissaoService = permissaoService;
    }

    public PerfilUsuario buscarPermissaoCPF(Usuario cpf) {
        return permissaoService.findByCpfUuario(cpf);
    }

    public LazyDataModel<PerfilUsuario> getList() {
        return list;
    }

    public void setList(LazyDataModel<PerfilUsuario> list) {
        this.list = list;
    }

    public UsuarioService getUsuarioService() {
        return usuarioService;
    }

    public void setUsuarioService(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    public Usuario getUsuarioSelecionado() {
        return UsuarioSelecionado;
    }

    public void setUsuarioSelecionado(Usuario UsuarioSelecionado) {
        this.UsuarioSelecionado = UsuarioSelecionado;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public Servidor getServidor() {
        return servidor;
    }

    public void setServidor(Servidor servidor) {
        this.servidor = servidor;
    }

    public ServidorService getServidorService() {
        return servidorService;
    }

    public void setServidorService(ServidorService servidorService) {
        this.servidorService = servidorService;
    }

    public List<PerfilUsuario> getListaPermissao() {
        return listaPermissao;
    }

    public List<PerfilUsuario> getListaPermissaoContemPermissao() {
        if (listaPermissao != null) {
            List<PerfilUsuario> retornoPer = new ArrayList<PerfilUsuario>();
            for (PerfilUsuario per : listaPermissao) {
                if (per.getPerfilUsuarioOrgaosAtivos().size() > 0) {
                    retornoPer.add(per);
                }
            }
            return retornoPer;
        } else {
            return null;
        }
    }

    public void setListaPermissao(List<PerfilUsuario> listaPermissao) {
        this.listaPermissao = listaPermissao;
    }

}
