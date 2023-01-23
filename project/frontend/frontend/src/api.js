export const fetchStatistics = async () => {
    const response = await fetch('/v1/ticket/statistics')
    return await response.text()
}