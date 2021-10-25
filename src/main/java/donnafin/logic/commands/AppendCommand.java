package donnafin.logic.commands;

import static donnafin.logic.parser.CliSyntax.PREFIX_COMMISSION;
import static donnafin.logic.parser.CliSyntax.PREFIX_INSURED_VALUE;
import static donnafin.logic.parser.CliSyntax.PREFIX_INSURER;
import static donnafin.logic.parser.CliSyntax.PREFIX_NAME;
import static donnafin.logic.parser.CliSyntax.PREFIX_REMARKS;
import static donnafin.logic.parser.CliSyntax.PREFIX_TYPE;
import static donnafin.logic.parser.CliSyntax.PREFIX_VALUE;
import static donnafin.logic.parser.CliSyntax.PREFIX_YEARLY_PREMIUM;
import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

import donnafin.logic.PersonAdapter;
import donnafin.logic.commands.exceptions.CommandException;
import donnafin.model.Model;
import donnafin.model.person.Asset;
import donnafin.model.person.Liability;
import donnafin.model.person.Policy;


public class AppendCommand extends Command {

    public static final String COMMAND_WORD = "append";

    public static final String MESSAGE_USAGE = COMMAND_WORD + "notes down a policy/asset/liability owned by a client. "
        + "Parameters (For Policies): "
        + PREFIX_NAME + "POLICY NAME "
        + PREFIX_INSURER + "INSURER "
        + PREFIX_INSURED_VALUE + "INSURED VALUE "
        + PREFIX_YEARLY_PREMIUM + "YEARLY PREMIUM "
        + PREFIX_COMMISSION + "COMMISSION \n"
        + "Parameters (For Assets/Liabilities): "
        + PREFIX_NAME + "ASSET/LIABILITY NAME "
        + PREFIX_TYPE + "TYPE "
        + PREFIX_VALUE + "VALUE "
        + PREFIX_REMARKS + "REMARKS ";

    public static final String MESSAGE_SUCCESS = "New policy/asset/liability added";

    private final Consumer<PersonAdapter> editor;
    private final PersonAdapter personAdapter;
    private final Object hashableNewValue;

    /**
     * The append command used to add a new policy associated with the client.
     * @param personAdapter the client being edited.
     * @param policy the new policy being added.
     */
    public AppendCommand(PersonAdapter personAdapter, Policy policy) {
        this.personAdapter = personAdapter;
        this.hashableNewValue = policy;
        this.editor = pa -> {
            Set<Policy> policies = new HashSet<>(pa.getSubject().getPolicies());
            policies.add(policy);
            pa.editPolicies(policies);
        };
    }

    /**
     * The append command used to add a new liability associated with the client.
     * @param personAdapter the client being edited.
     * @param liability the new liability being added.
     */
    public AppendCommand(PersonAdapter personAdapter, Liability liability) {
        this.personAdapter = personAdapter;
        this.hashableNewValue = liability;
        this.editor = pa -> {
            Set<Liability> liabilities = new HashSet<>(pa.getSubject().getLiabilities());
            liabilities.add(liability);
            pa.editLiabilities(liabilities);
        };
    }

    /**
     * The append command used to add a new asset associated with the client.
     * @param personAdapter the client being edited.
     * @param asset the new asset being added.
     */
    public AppendCommand(PersonAdapter personAdapter, Asset asset) {
        this.personAdapter = personAdapter;
        this.hashableNewValue = asset;
        this.editor = pa -> {
            Set<Asset> assets = new HashSet<>(pa.getSubject().getAssets());
            assets.add(asset);
            pa.editAssets(assets);
        };
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        editor.accept(this.personAdapter);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof AppendCommand)) {
            return false;
        }
        return ((AppendCommand) o).personAdapter.equals(personAdapter)
                && ((AppendCommand) o).hashableNewValue.hashCode() == this.hashableNewValue.hashCode();
    }

    @Override
    public int hashCode() {
        return Objects.hash(personAdapter, hashableNewValue);
    }
}