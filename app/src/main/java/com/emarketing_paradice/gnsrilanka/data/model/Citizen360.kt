package com.emarketing_paradice.gnsrilanka.data.model

data class Citizen360(
        val citizen: Citizen,
        val welfarePrograms: List<WelfareProgram> = emptyList(),
        val permits: List<Permit> = emptyList(),
        val dailyLogs: List<DailyLog> = emptyList(),
        val pensions: List<Pension> = emptyList(),
        val elderlyIds: List<ElderlyId> = emptyList(),
        val voluntaryOrgs: List<VoluntaryOrg> =
                emptyList() // Typically not by person, but maybe if they are an officer?
)
