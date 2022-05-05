package ua.ferret.client.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ua.ferret.client.model.ProposalStatus;
import ua.ferret.client.service.AccountService;
import ua.ferret.client.service.ProposalService;
import ua.ferret.client.service.TokenService;

@RestController
@RequestMapping("/props")
public class ProposalController {
	
	private @Autowired ProposalService proposalS;
	private @Autowired AccountService accountS;
	private @Autowired TokenService tokenS;

	@GetMapping("/create")
	public Object create(HttpServletRequest request) {
		var claims = tokenS.parseToken(request);
		var account = accountS.validationAccount(claims);
		var proposal = proposalS.createProposal(request, account);
		return ResponseEntity.of(proposal);
	}

	@GetMapping("/edit")
	public Object edit(HttpServletRequest request) {
		var claims = tokenS.parseToken(request);
		var account = accountS.validationAccount(claims);
		var proposal = proposalS.editProposal(request, account);
		return ResponseEntity.of(proposal);
	}
	
	@CrossOrigin
	@GetMapping("/list")
	public Object list(HttpServletRequest request) {
		return proposalS.list(ProposalStatus.values());
	}
	
	@GetMapping("/own")
	public Object own(HttpServletRequest request) {
		var claims = tokenS.parseToken(request);
		var account = accountS.validationAccount(claims);
		return proposalS.list(account);
	}
	
	@GetMapping("/somehitng")
	public Object somehitng(HttpServletRequest request) {
		return ResponseEntity.badRequest();
	}
	
}
