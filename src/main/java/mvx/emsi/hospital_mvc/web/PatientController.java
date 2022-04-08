package mvx.emsi.hospital_mvc.web;

import lombok.AllArgsConstructor;
import mvx.emsi.hospital_mvc.entities.Patient;
import mvx.emsi.hospital_mvc.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.naming.Binding;
import javax.validation.Valid;
import java.util.List;

@Controller @AllArgsConstructor
public class PatientController {
     PatientRepository patientRepository;
     @GetMapping(path = "/index")
     public String patients(Model model, @RequestParam(name = "page",defaultValue = "0") int page, @RequestParam(name = "size",defaultValue = "5") int size,@RequestParam(name = "keyword",defaultValue = "") String keyword){
         Page<Patient> pagepatients=patientRepository.findByNomContains(keyword,PageRequest.of(page, size));
         model.addAttribute("pages",new int[pagepatients.getTotalPages()]);
         model.addAttribute("listPatients",pagepatients.getContent());
         model.addAttribute("CurrentPage",page);
         model.addAttribute("keyword",keyword);


         return "patients";

     }
     @GetMapping(path = "/delete")
    public String delete(Long id){
         patientRepository.deleteById(id);

         return "redirect:/index";
     }
    @GetMapping(path = "/")
    public String home(Long id,String keyword,int page){
        return "redirect:/index?page="+page+"&keyword="+keyword;
    }
    @GetMapping(path = "/formPatients")
    public String formPatients(Model model){
         model.addAttribute("patient",new Patient());
         return "formPatients";
    }
    @GetMapping(path = "/save")
    public String save(Model model, @Valid Patient patient, BindingResult bindingResult){
         if(bindingResult.hasErrors()) return "formPatients";
         patientRepository.save(patient);
         return "formPatients";

    }

}
