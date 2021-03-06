package me.biocomp.hubitat_ci_example

import me.biocomp.hubitat_ci.api.app_api.AppExecutor
import me.biocomp.hubitat_ci.api.common_api.Log
import me.biocomp.hubitat_ci.app.HubitatAppSandbox
import spock.lang.Specification

class Test extends
        Specification
{
    // Creating a sandbox object for device script from file.
    // At this point, script object is not created.
    // Using Hubitat**App**Sandbox for app scripts.
    HubitatAppSandbox sandbox = new HubitatAppSandbox(new File("appscript.groovy"))

    def "Basic validation"() {
        expect:
            // Compile, construct script object, and validate definition() and preferences()
            sandbox.run()
    }

    def "installed() logs 'Num'"() {
        setup:
            // Create mock log
            def log = Mock(Log)

            // Make AppExecutor return the mock log
            AppExecutor api = Mock{ _ * getLog() >> log }

            // Parse, construct script object, run validations
            def script = sandbox.run(api: api, userSettingValues: [Num: 42])

        when:
            // Run installed() method on script object.
            script.installed()

        then:
            // Expect that log.info() was called with this string
            1 * log.info("Installed with number = 42")
    }
}