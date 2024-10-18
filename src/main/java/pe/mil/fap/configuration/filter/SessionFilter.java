package pe.mil.fap.configuration.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pe.mil.fap.common.constants.Configuracion;
import pe.mil.fap.model.security.RolDTO;
import pe.mil.fap.model.security.UsuarioDTO;

@Component
public class SessionFilter implements Filter {

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		
		List<RolDTO> lstRoles = new ArrayList<>();
		lstRoles.add(new RolDTO(1, "ROLE_ADMIN", "ADMINISTRADOR"));
		lstRoles.add(new RolDTO(2, "ROLE_JEOPE", "JEFE DE OPERACIONES"));
		lstRoles.add(new RolDTO(3, "ROLE_COESC", "COMANDO DE ESCUA	DRON"));
		lstRoles.add(new RolDTO(4, "ROLE_JEINS", "JEFE DE INSTRUCCION"));
		//TTAA
		lstRoles.add(new RolDTO(5, "ROLE_INSTR", "INSTRUCTOR"));
		lstRoles.add(new RolDTO(6, "ROLE_ALUMN", "ALUMNO"));
		
		lstRoles.add(new RolDTO(7, "ROLE_USER", "USUARIO CONSULTA"));
		
		//Esto me devolvera mi usuario, (Usuario / Contraseña)
		UsuarioDTO usuarioLogeado = UsuarioDTO.builder().idUnidad(5)//283(ALAR1) 5(GRU51), 144(ALAR6), 1(COFAP), 323(GRUP7)
														.noNombre("Rosmery Rivero")
														.coNsa("97177") 
														.idEscuadron(1)//1, 510
														.lstRoles(lstRoles)
														.build();
		if (usuarioLogeado != null) { 
			request.getSession().setAttribute(Configuracion.Helper.USUARIO_LOGEADO, usuarioLogeado); 
		} 
		chain.doFilter(request, response);
	}

}
