package RailServlet;

import Railroad.DaoService;
import Service.MessageBean;
import Service.Offer;
import Service.ServiceBean;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Created by VBuevich on 08.09.2016.
 */
public class Ticketing extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        ServiceBean bean = ServiceBean.get(session);
        MessageBean message = MessageBean.get(session);

        String departureTime = request.getParameter("departureTime");
        String departureStation = request.getParameter("departureStation");
        String arrivalTime = request.getParameter("arrivalTime");
        String arrivalStation = request.getParameter("arrivalStation");

        List<Offer> offers = DaoService.getOffers(departureStation, departureTime, arrivalStation, arrivalTime);

        if (offers == null || offers.size() == 0) {
            message.setErrorMessage("No offers found using your criteria of search");
            bean.setOfferList(null);
        }
        else {
            message.setErrorMessage(null);
            bean.setOfferList(offers);
        }

        response.sendRedirect(request.getContextPath() + "/findTrain.jsp");
    }


}