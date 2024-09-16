package pe.mil.fap.configuration.filter;

import java.io.IOException;

import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pe.mil.fap.common.constants.Configuracion;
import pe.mil.fap.model.security.UsuarioDTO;

@Component
public class SessionFilter implements Filter {

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
			
		UsuarioDTO usuarioLogeado = UsuarioDTO.builder().idUnidad(5)//283(ALAR1) 5(GRU51), 144(ALAR6), 1(COFAP), 323(GRUP7)
														.noNombre("USUARIO COMOP")
														.build();
		if (usuarioLogeado != null) {
			request.getSession().setAttribute(Configuracion.Helper.USUARIO_LOGEADO, usuarioLogeado);
		} 
		chain.doFilter(request, response);
	}

}
