/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.to.secad.aede.view;

import br.gov.to.secad.ergon.domain.Servidor;
import br.gov.to.secad.aede.domain.Usuario;
import br.gov.to.secad.ergon.service.ServidorService;
import br.gov.to.secad.aede.service.UsuarioService;
import br.gov.to.secad.aede.util.JDialogoUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.primefaces.context.RequestContext;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

/**
 *
 * @author alex.santos
 */
@ManagedBean
@ViewScoped
public class UsuarioController implements Serializable {

    private String cpfServidor;
    private String emailServidor;

    private Usuario usuario;

    private Usuario UsuarioSelecionado;

    public Servidor servidor;
    /**
     * Atributo que instancia o serviço de transações com a classe Servidor
     */
    @ManagedProperty(value = "#{servidorService}")
    private ServidorService servidorService;

    @ManagedProperty(value = "#{usuarioService}")
    private UsuarioService usuarioService;

    /*
     list é uma instancia do DATAMODEL da dataTable
     */
    private transient LazyDataModel<Usuario> list;

    public void init() {

        this.list = new LazyDataModel<Usuario>() {
            @Override
            public List<Usuario> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

                //Variavel de retorno
                List<Usuario> result;
                //Seta o tamanho da pagina
                this.setPageSize(pageSize);

                if (sortOrder == SortOrder.UNSORTED || StringUtils.isBlank(sortField)) {
                    //Atribui para o request em qual pagina vai e o tamanho da pagina                    
                    PageRequest request = new PageRequest(first / pageSize, pageSize);
                    //Faz a consulta no banco passando o request e o os filtros para o service
                    Page<Usuario> page;
                    page = usuarioService.findAllUsuario(request,
                            filters.get("username"), filters.get("email"));
                    //Atribui a quantidade de registro total
                    this.setRowCount((int) page.getTotalElements());
                    // Pega a lista de registro que irá mostrar na pagina
                    result = page.getContent();
                } else {
                    /*
                     Faz a consulta se é decrescente ou cresente de acordo o fild que foi selecionado na Grid
                     */
                    Sort sort = new Sort(sortOrder == SortOrder.ASCENDING ? Sort.Direction.ASC : Sort.Direction.DESC, sortField);

                    PageRequest request = new PageRequest(first / pageSize, pageSize, sort);
                    Page<Usuario> page = usuarioService.findAllUsuario(request, filters.get("username"), filters.get("email"));
                    this.setRowCount((int) page.getTotalElements());
                    result = page.getContent();
                }
                // retorna a lista com os registro
                return result;
            }

            @Override
            public Object getRowKey(Usuario object) {
                return object.getId().toString();
            }

            @Override
            public Usuario getRowData(String rowKey) {
                return usuarioService.findOneUsuario(Integer.parseInt(rowKey));
            }
        };
    }

    public LazyDataModel<Usuario> getList() {
        return list;
    }

    public void setList(LazyDataModel<Usuario> list) {
        this.list = list;
    }

    public String getCpfServidor() {
        return cpfServidor;
    }

    public void setCpfServidor(String cpfServidor) {
        this.cpfServidor = cpfServidor;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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

    public void abrirJDialogUsuario() {
        JDialogoUtil.abrirDialogo("usuarioJdialogView", "600", "100%", true, false, null);
    }
    /*
     Fechar o dialog e enviar por envente o objeto selecionado.
     */

    public String getEmailServidor() {
        return emailServidor;
    }

    public void setEmailServidor(String emailServidor) {
        this.emailServidor = emailServidor;
    }

    public void selecionardoUsuario(Usuario usuario) {
        RequestContext.getCurrentInstance().closeDialog(usuario);
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
    /*
     Pega o evento do JDIALOG que foi selecionado e atribui para o concedente
     */

    public void usuarioJDialogSelecionado(SelectEvent event) {
        UsuarioSelecionado = (Usuario) event.getObject();
        this.servidor = servidorService.findByCpfInterno(UsuarioSelecionado.getCpf());

    }

    public void consultarServidor() {
    }

}
