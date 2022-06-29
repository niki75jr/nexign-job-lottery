package com.n75jr.nexign.lottery.shell;

import com.n75jr.nexign.lottery.domain.Participant;
import com.n75jr.nexign.lottery.service.IOService;
import com.n75jr.nexign.lottery.service.ParticipantService;
import com.n75jr.nexign.lottery.util.ShellUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import javax.validation.Validator;

@Log4j2
@ShellComponent
@ShellCommandGroup("Participant")
@RequiredArgsConstructor
public class ParticipantShell {

    private final Validator validator;
    private final IOService ioService;
    private final ParticipantService participantService;

    @ShellMethod(value = "Print all participant from db", key = "participantAll")
    public void printAllParticipant() {
        var num = participantService.count();

        if (num == 0) {
            ioService.println("List of participants is empty");
            return;
        }
        else if (num > 10) {
            ioService.println("Number of participants: " + num);
            String answer;
            do {
                ioService.print("Do you want to display all?: (y/n)> ");
                answer = ioService.readString();
            } while (!answer.equalsIgnoreCase("y") && !answer.equalsIgnoreCase("n"));
            if (answer.equalsIgnoreCase("n")) {
                return;
            }
        }

        var participants = participantService.findAll();
        ShellUtils.printItems(participants, ioService, p -> p.toString());
    }

    @ShellMethod(value = "Add a new participant to db", key = "participantAdd")
    public void addNew() {
        Participant participant;
        try {
            ioService.print("First name: > ");
            var fName = ioService.readString();
            ioService.print("Last name: > ");
            var lName = ioService.readString();
            ioService.print("Age: > ");
            var age = Integer.parseInt(ioService.readString());
            ioService.print("City: > ");
            var city = ioService.readString();
            participant = Participant.of(null, fName, lName, age, city);
        } catch (Exception ex) {
            ioService.println("Error!");
            return;
        }

        var validate = validator.validate(participant);
        if (validate.isEmpty()) {
            var savedParticipant = participantService.save(participant);
            ioService.println("The participant successfully saved with id #" + savedParticipant.getId());
        } else {
            ioService.println("The participant was not saved");
        }
    }

    @ShellMethod(value = "Generate some participants", key = "participantGen")
    public void generateNew(
            @ShellOption(value = "size") int size,
            @ShellOption(value = "show", defaultValue = "false") String show) {
        var _show = Boolean.valueOf(show.toLowerCase());
        if (log.isDebugEnabled()) {
            log.debug("size: {}, show: {}", size, _show);
        }

        if (size < 1 || size > 100) {
            ioService.println("Error: non valid argument");
            return;
        }

        var participantsGenerated = participantService.generate(size);
        var participantsSaved = participantService.saveAll(participantsGenerated);
        ioService.println("Successfully saved " + participantsSaved.size() + " participants");

        if (_show) {
            ShellUtils.printItems(participantsSaved, ioService, participant -> participant.toString());
        }
    }
}

