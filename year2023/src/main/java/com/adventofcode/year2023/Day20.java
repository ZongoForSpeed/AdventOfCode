package com.adventofcode.year2023;

import com.google.common.base.Splitter;
import jakarta.annotation.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public final class Day20 {

    private static final Logger LOGGER = LoggerFactory.getLogger(Day20.class);
    private static final Pattern PATTERN = Pattern.compile("^(.*) -> (.*)$");

    private Day20() {
        // No-Op
    }

    /**
     * --- Day 20: Pulse Propagation ---
     * <p>
     * With your help, the Elves manage to find the right parts and fix all of the
     * machines. Now, they just need to send the command to boot up the machines
     * and get the sand flowing again.
     * <p>
     * The machines are far apart and wired together with long cables. The cables
     * don't connect to the machines directly, but rather to communication modules
     * attached to the machines that perform various initialization tasks and also
     * act as communication relays.
     * <p>
     * Modules communicate using pulses. Each pulse is either a high pulse or a
     * low pulse. When a module sends a pulse, it sends that type of pulse to each
     * module in its list of destination modules.
     * <p>
     * There are several different types of modules:
     * <p>
     * Flip-flop modules (prefix %) are either on or off; they are initially off.
     * If a flip-flop module receives a high pulse, it is ignored and nothing
     * happens. However, if a flip-flop module receives a low pulse, it flips
     * between on and off. If it was off, it turns on and sends a high pulse. If
     * it was on, it turns off and sends a low pulse.
     * <p>
     * Conjunction modules (prefix &) remember the type of the most recent pulse
     * received from each of their connected input modules; they initially default
     * to remembering a low pulse for each input. When a pulse is received, the
     * conjunction module first updates its memory for that input. Then, if it
     * remembers high pulses for all inputs, it sends a low pulse; otherwise, it
     * sends a high pulse.
     * <p>
     * There is a single broadcast module (named broadcaster). When it receives a
     * pulse, it sends the same pulse to all of its destination modules.
     * <p>
     * Here at Desert Machine Headquarters, there is a module with a single button
     * on it called, aptly, the button module. When you push the button, a single
     * low pulse is sent directly to the broadcaster module.
     * <p>
     * After pushing the button, you must wait until all pulses have been
     * delivered and fully handled before pushing it again. Never push the button
     * if modules are still processing pulses.
     * <p>
     * Pulses are always processed in the order they are sent. So, if a pulse
     * is sent to modules a, b, and c, and then module a processes its pulse and
     * sends more pulses, the pulses sent to modules b and c would have to be
     * handled first.
     * <p>
     * The module configuration (your puzzle input) lists each module. The name of
     * the module is preceded by a symbol identifying its type, if any. The name
     * is then followed by an arrow and a list of its destination modules. For
     * example:
     * <p>
     * broadcaster -> a, b, c
     * %a -> b
     * %b -> c
     * %c -> inv
     * &inv -> a
     * <p>
     * In this module configuration, the broadcaster has three destination modules
     * named a, b, and c. Each of these modules is a flip-flop module (as
     * indicated by the % prefix). a outputs to b which outputs to c which outputs
     * to another module named inv. inv is a conjunction module (as indicated by
     * the & prefix) which, because it has only one input, acts like an inverter
     * (it sends the opposite of the pulse type it receives); it outputs to a.
     * <p>
     * By pushing the button once, the following pulses are sent:
     * <p>
     * button -low-> broadcaster
     * broadcaster -low-> a
     * broadcaster -low-> b
     * broadcaster -low-> c
     * a -high-> b
     * b -high-> c
     * c -high-> inv
     * inv -low-> a
     * a -low-> b
     * b -low-> c
     * c -low-> inv
     * inv -high-> a
     * <p>
     * After this sequence, the flip-flop modules all end up off, so pushing the
     * button again repeats the same sequence.
     * <p>
     * Here's a more interesting example:
     * <p>
     * broadcaster -> a
     * %a -> inv, con
     * &inv -> b
     * %b -> con
     * &con -> output
     * <p>
     * This module configuration includes the broadcaster, two flip-flops (named a
     * and b), a single-input conjunction module (inv), a multi-input conjunction
     * module (con), and an untyped module named output (for testing purposes).
     * The multi-input conjunction module con watches the two flip-flop modules
     * and, if they're both on, sends a low pulse to the output module.
     * <p>
     * Here's what happens if you push the button once:
     * <p>
     * button -low-> broadcaster
     * broadcaster -low-> a
     * a -high-> inv
     * a -high-> con
     * inv -low-> b
     * con -high-> output
     * b -high-> con
     * con -low-> output
     * <p>
     * Both flip-flops turn on and a low pulse is sent to output! However, now
     * that both flip-flops are on and con remembers a high pulse from each of its
     * two inputs, pushing the button a second time does something different:
     * <p>
     * button -low-> broadcaster
     * broadcaster -low-> a
     * a -low-> inv
     * a -low-> con
     * inv -high-> b
     * con -high-> output
     * <p>
     * Flip-flop a turns off! Now, con remembers a low pulse from module a, and so
     * it sends only a high pulse to output.
     * <p>
     * Push the button a third time:
     * <p>
     * button -low-> broadcaster
     * broadcaster -low-> a
     * a -high-> inv
     * a -high-> con
     * inv -low-> b
     * con -low-> output
     * b -low-> con
     * con -high-> output
     * <p>
     * This time, flip-flop a turns on, then flip-flop b turns off. However,
     * before b can turn off, the pulse sent to con is handled first, so it
     * briefly remembers all high pulses for its inputs and sends a low pulse to
     * output. After that, flip-flop b turns off, which causes con to update its
     * state and send a high pulse to output.
     * <p>
     * Finally, with a on and b off, push the button a fourth time:
     * <p>
     * button -low-> broadcaster
     * broadcaster -low-> a
     * a -low-> inv
     * a -low-> con
     * inv -high-> b
     * con -high-> output
     * <p>
     * This completes the cycle: a turns off, causing con to remember only low
     * pulses and restoring all modules to their original states.
     * <p>
     * To get the cables warmed up, the Elves have pushed the button 1000 times.
     * How many pulses got sent as a result (including the pulses sent by the
     * button itself)?
     * <p>
     * In the first example, the same thing happens every time the button is
     * pushed: 8 low pulses and 4 high pulses are sent. So, after pushing the
     * button 1000 times, 8000 low pulses and 4000 high pulses are sent.
     * Multiplying these together gives 32000000.
     * <p>
     * In the second example, after pushing the button 1000 times, 4250 low pulses
     * and 2750 high pulses are sent. Multiplying these together gives 11687500.
     * <p>
     * Consult your module configuration; determine the number of low pulses and
     * high pulses that would be sent after pushing the button 1000 times, waiting
     * for all pulses to be fully handled after each push of the button. What do
     * you get if you multiply the total number of low pulses sent by the total
     * number of high pulses sent?
     */
    public static final class PartOne {

        private PartOne() {
            // No-Op
        }

        public static Map<Value, Long> pulsePropagation(Scanner scanner) {
            Inputs inputs = readInputs(scanner);
            Map<String, Module> modules = inputs.modules();

            LOGGER.info("modules: {}", modules);

            EnumMap<Value, Long> counters = new EnumMap<>(Value.class);
            int cycles = 1000;
            for (int i = 0; i < cycles; ++i) {
                Deque<Pulse> pulses = new ArrayDeque<>();
                pulses.addLast(new Pulse("button", "broadcaster", Value.LOW));

                while (!pulses.isEmpty()) {
                    Pulse pulse = pulses.pollFirst();
                    counters.merge(pulse.value, 1L, Long::sum);
                    Module module = modules.get(pulse.to);
                    if (module == null) {
                        continue;
                    }
                    Value value = module.propagate(pulse);
                    if (value == null) {
                        continue;
                    }
                    for (String destination : module.destinations) {
                        pulses.addLast(new Pulse(module.label, destination, value));
                    }
                }
            }
            return counters;
        }
    }

    /**
     * --- Part Two ---
     * <p>
     * The final machine responsible for moving the sand down to Island Island has
     * a module attached named rx. The machine turns on when a single low pulse is
     * sent to rx.
     * <p>
     * Reset all modules to their default states. Waiting for all pulses to be
     * fully handled after each button press, what is the fewest number of button
     * presses required to deliver a single low pulse to the module named rx?
     */
    public static final class PartTwo {
        private PartTwo() {
            // No-Op
        }

        public static long pulsePropagation(Scanner scanner) {
            Inputs inputs = readInputs(scanner);
            Map<String, Module> modules = inputs.modules;
            List<String> rxInputs = inputs.rxInputs;

            Map<String, Map<String, Integer>> inputCycles = new HashMap<>();
            for (String input : rxInputs) {
                Map<String, Integer> cycle = inputCycles.computeIfAbsent(input, ignore -> new HashMap<>());
                for (Module module : modules.values()) {
                    if (module.destinations.contains(input)) {
                        cycle.put(module.label, 1);
                    }
                }
            }

            for (int cycle = 0; cycle < 5000; ++cycle) {
                Deque<Pulse> pulses = new ArrayDeque<>();
                pulses.addLast(new Pulse("button", "broadcaster", Value.LOW));

                while (!pulses.isEmpty()) {
                    Pulse pulse = pulses.pollFirst();

                    for (String input : rxInputs) {
                        Map<String, Integer> counter = Objects.requireNonNull(inputCycles.get(input));
                        if (pulse.value == Value.HIGH
                            && pulse.to.equals(input)
                            && counter.containsKey(pulse.from)
                            && counter.get(pulse.from).equals(1)) {
                            counter.merge(pulse.from, cycle, Integer::sum);
                        }
                    }

                    Module module = modules.get(pulse.to);
                    if (module == null) {
                        continue;
                    }
                    Value value = module.propagate(pulse);
                    if (value == null) {
                        continue;
                    }
                    for (String destination : module.destinations) {
                        pulses.addLast(new Pulse(module.label, destination, value));
                    }
                }
            }

            LOGGER.info("inputCycles  = {}", inputCycles);
            return inputCycles.values().stream().map(Map::values).flatMap(Collection::stream).mapToLong(l -> l).reduce(1L, (a, b) -> a * b);
        }
    }

    private static Inputs readInputs(Scanner scanner) {
        Map<String, Module> modules = new HashMap<>();
        Map<String, List<String>> inputs = new HashMap<>();
        List<String> rxInputs = new ArrayList<>();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            Matcher matcher = PATTERN.matcher(line);
            if (matcher.find()) {
                String label = matcher.group(1);
                char type = label.charAt(0);
                if (type != 'b') {
                    label = label.substring(1);
                }

                List<String> destinations = Splitter.on(", ").splitToList(matcher.group(2));
                if (destinations.contains("rx")) {
                    rxInputs.add(label);
                }
                for (String d : destinations) {
                    inputs.computeIfAbsent(d, ignore -> new ArrayList<>()).add(label);
                }

                Module module = new Module(label, type, destinations);
                if (type == '%') {
                    module.on.set(false);
                }
                modules.put(label, module);
            } else {
                throw new IllegalStateException("Cannot parse line: " + line);
            }

        }

        for (Map.Entry<String, List<String>> entry : inputs.entrySet()) {
            String label = entry.getKey();
            Module module = modules.get(label);
            if (module != null && module.type == '&') {
                List<String> list = entry.getValue();
                Map<String, Value> collect = list.stream().collect(Collectors.toMap(p -> p, ignore -> Value.LOW));
                module.inputs.putAll(collect);
            }
        }

        return new Inputs(modules, rxInputs);
    }

    public enum Value {
        HIGH, LOW;
    }

    record Inputs(Map<String, Module> modules, List<String> rxInputs) {
    }

    record Pulse(String from, String to, Value value) {
    }

    record Module(String label, char type, AtomicBoolean on, Map<String, Value> inputs, List<String> destinations) {
        public Module(String label, char type, List<String> destinations) {
            this(label, type, new AtomicBoolean(true), new HashMap<>(), destinations);
        }

        @Nullable
        Value propagate(Pulse pulse) {
            return switch (type) {
                case 'b' -> pulse.value;
                case '%' -> {
                    if (pulse.value == Value.HIGH) {
                        yield null;
                    }
                    on.set(!on.get());
                    yield on.get() ? Value.HIGH : Value.LOW;
                }
                case '&' -> {
                    inputs.put(pulse.from, pulse.value);
                    for (Value value : inputs.values()) {
                        if (value == Value.LOW) {
                            yield Value.HIGH;
                        }
                    }
                    yield Value.LOW;
                }
                default -> throw new IllegalStateException();
            };
        }
    }
}
